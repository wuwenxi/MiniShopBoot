package com.wwx.minishop.service;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.entity.Product;
import com.wwx.minishop.exception.ProductException;

import java.util.List;

public interface ProductService {

    List<Product> findProductListWithShopId(Integer shopId);

    int addProduct(Product product, ImageHolder image, List<ImageHolder> productImgList);

    int modifyProduct(Product product,ImageHolder image,List<ImageHolder> productImgList) throws ProductException;

    Product findProduct(Integer productId);
}
