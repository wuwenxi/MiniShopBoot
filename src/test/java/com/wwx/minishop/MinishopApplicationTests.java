package com.wwx.minishop;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.dao.ProductCategoryMapper;
import com.wwx.minishop.dao.ProductMapper;
import com.wwx.minishop.dao.ShopCategoryMapper;
import com.wwx.minishop.dao.ShopMapper;
import com.wwx.minishop.entity.*;
import com.wwx.minishop.enums.ShopStateEnum;
import com.wwx.minishop.exception.ProductException;
import com.wwx.minishop.execution.ShopExecution;
import com.wwx.minishop.repository.*;
import com.wwx.minishop.service.ProductService;
import com.wwx.minishop.service.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MinishopApplicationTests {

    @Autowired
    PersonInfoRepository personInfoRepository;

    @Autowired
    ShopCategoryRepository shopCategoryRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ShopService shopService;

    @Autowired
    ProductService productService;

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    ShopCategoryMapper shopCategoryMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    /*@Qualifier("productCacheManager")//指定缓存管理器
    @Autowired
    RedisCacheManager productCacheManager;*/

    @Autowired
    LocalAuthRepository localAuthRepository;

    @Test
    public void testShiro(){
        LocalAuth localAuth = localAuthRepository.queryByUserName("wws");
        System.out.println(localAuth);
        /*PersonInfo personInfo = new PersonInfo("xoxox","123",null,null,null,null,
                null,1,new Date(),new Date());
        personInfoRepository.save(personInfo);*/
        /*PersonInfo personInfo = new PersonInfo();
        personInfo.setPersonInfoId(1);
        LocalAuth auth = new LocalAuth(personInfo,"wwx","123",new Date(),new Date());
        localAuthRepository.save(auth);*/
    }

    @Test
    public void testRedis(){
        //stringRedisTemplate.opsForValue().append("test","test01");
        /*String test = stringRedisTemplate.opsForValue().get("test");
        System.out.println("redisTest:"+test);*/
        Product product = new Product();
        product.setProductId(1);
        product.setEnableStatus(0);
        product.setProductName("test");
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        //productRedisTemplate.opsForValue().set("test1",product);
        //Product product1 = productRedisTemplate.opsForValue().get("test1");
        //System.out.println(product1);
    }

    @Test
    public void test01()  {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1);
        productCategory.setProductCategoryName("原味咖啡");
        int num = productCategoryMapper.updateProductCategory(productCategory);
        System.out.println();
    }

    @Test
    public void testFileUpload() {

        Shop shop = new Shop();
        shop.setShopId(1);
        File file = new File("E:\\Spring\\img\\shuixianhua.jpg");
        try {
            InputStream in = new FileInputStream(file);
            ImageHolder holder = new ImageHolder("shuixianhua.jpg", in);
            shopService.modifyShop(shop, holder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProduct() {
        ShopCategory parentCategory = new ShopCategory();
        ShopCategory childCategory = new ShopCategory();
        parentCategory.setShopCategoryId(3);
        childCategory.setParent(parentCategory);

        List<ShopCategory> shopCategoryList = shopCategoryMapper.queryForListShopCategory(childCategory);
        System.out.println("店铺类别个数：" + shopCategoryList.size());
        for (ShopCategory shopCategory : shopCategoryList) {
            System.out.println(shopCategory);
        }
    }

    @Test
    public void testPersonInfo() {
      /*  PersonInfo personInfo = new PersonInfo(1, "吴文锡", "男", "wwx@springboot.com", null
                , 1, new Date(), new Date(), 0);*/
       // personInfoRepository.saveAndFlush(personInfo);
    }

    @Test
    public void testShopCategory() {
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(8);
        ShopCategory shopCategory0 = new ShopCategory(null, "日常用品", "日常用品", 0,
                new Date(), new Date(), parent);
        ShopCategory shopCategory1 = new ShopCategory(null, "洗涤", "洗涤", 0,
                new Date(), new Date(), parent);
        ShopCategory shopCategory2 = new ShopCategory(null, "杂货铺", "杂货铺", 0,
                new Date(), new Date(), parent);
        ShopCategory shopCategory3 = new ShopCategory(null, "文具店", "文具店", 0,
                new Date(), new Date(), parent);


        List<ShopCategory> shopCategoryArrayList = new ArrayList<>();
        shopCategoryArrayList.add(shopCategory0);
        shopCategoryArrayList.add(shopCategory1);
        shopCategoryArrayList.add(shopCategory2);
        shopCategoryArrayList.add(shopCategory3);

        shopCategoryRepository.saveAll(shopCategoryArrayList);
    }

    @Test
    public void testShop() {

        /*PersonInfo info = new PersonInfo();
        info.setUserId(1);
        Area area = new Area();
        area.setAreaId(1);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1);

        Shop shop = new Shop(null,info,area,shopCategory,"test",
                "test","四川省德阳市旌阳区黄许镇三合村2组","123456789",null,90,
                new Date(),new Date(),0,null);
        shopRepository.save(shop);*/
        Shop shop = new Shop();
        PersonInfo info = new PersonInfo();
        info.setUserId(1);
        shop.setOwner(info);
        List<Shop> shopList = shopService.findShopListWithOwner(shop);
        System.out.println("店铺个数: " + shopList.size());
        System.out.println(shopList);
    }
}


