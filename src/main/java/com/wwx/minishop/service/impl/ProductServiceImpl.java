package com.wwx.minishop.service.impl;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.dao.ProductMapper;
import com.wwx.minishop.entity.Product;
import com.wwx.minishop.entity.ProductImg;
import com.wwx.minishop.exception.ProductException;
import com.wwx.minishop.exception.ProductImgException;
import com.wwx.minishop.repository.ProductImgRepository;
import com.wwx.minishop.repository.ProductRepository;
import com.wwx.minishop.service.ProductService;
import com.wwx.minishop.utils.ImageUtils;
import com.wwx.minishop.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.wwx.minishop.utils.InsertImageUtils.insertProductImg;
import static com.wwx.minishop.utils.InsertImageUtils.insertProductImgList;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductImgRepository productImgRepository;

    @Cacheable(cacheNames = "productList",key = "'shopId'+#shopId",unless = "#result==null")
    @Override
    public List<Product> findProductListWithShopId(Integer shopId) {
       // productRedisHelper.getProductListByShopId(shopId);
        return productMapper.queryAllByShopId(shopId);
    }

    @CacheEvict(cacheNames = "productList",key = "'shopId'+#product.shop.shopId")
    @Override
    public int addProduct(Product product, ImageHolder image, List<ImageHolder> productImgList) {
        if(product!=null && product.getShop()!=null && product.getShop().getShopId()!=null){

            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());

            //状态 0：下架 1：上架
            product.setEnableStatus(0);
            //保存图片  设置图片路径
            if(image!=null){
                insertProductImg(product,image);
            }else {
                return -1;
            }
            //保存商品
            try {
                productRepository.save(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //保存图片列表
            if(productImgList!=null&&productImgList.size()>0) {
                try {
                    insertProductImgList(product, productImgList);
                } catch (ProductImgException e) {
                    e.printStackTrace();
                }
            }else {
                return -1;
            }
            return 1;
        }else {
            return 0;
        }
    }

    @Caching(
            put = {
                    @CachePut(cacheNames = "product",key = "'product'+#product.productId")},
            evict = {
                    //清空更新商品所属店铺下的商品列表
                    @CacheEvict(cacheNames = "productList",key = "'shopId'+#product.shop.shopId")
            }
    )
    @Override
    public int modifyProduct(Product product,ImageHolder image ,List<ImageHolder> productImgList) throws ProductException {
        if(product!=null&&product.getProductId()>0){
            if(image!=null){
                try {
                    //删除原来的商品图片
                    Product pro = productMapper.queryProductById(product.getProductId());
                    if(pro.getImgAddress()!=null){
                        ImageUtils.deleteFileOrPath(pro.getImgAddress());
                    }
                    //插入更新图片
                    insertProductImg(product,image);
                } catch (Exception e) {
                    throw new ProductException("更新商品图片失败");
                }
            }
            if (productImgList!=null&&productImgList.size()>0){
                List<ProductImg> list = productImgRepository.queryProductImgsByProductId(product.getProductId());
                //遍历删除详情图片
                if(list!=null && list.size()>0){
                    for(ProductImg productImg:list){
                        //删除文件夹下的图片
                        ImageUtils.deleteFileOrPath(productImg.getImgAddress());
                    }
                    //删除数据库中的记录
                    productImgRepository.deleteByProductId(product.getProductId());
                }
                try {
                    //插入更新详细图片
                    List<ProductImg> productImgs = insertProductImgList(product, productImgList);
                    if(productImgs.size()>0){
                        try {
                            productImgRepository.saveAll(productImgs);
                        } catch (Exception e) {
                            throw new ProductImgException("添加商品详情图发生错误");
                        }
                    }
                } catch (ProductImgException e) {
                    e.printStackTrace();
                }
            }
            int num = productMapper.modifyProduct(product);
            if(num>0){
                return 1;
            }
        }
        return -1;
    }

    @Cacheable(cacheNames = "product",key = "'product'+#productId",unless = "#result==null")
    @Override
    public Product findProduct(Integer productId) {
        System.out.println("查询"+productId+"商品");
        return productMapper.queryProductById(productId);
    }
}
