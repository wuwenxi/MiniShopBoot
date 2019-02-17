package com.wwx.minishop;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.dao.ShopCategoryMapper;
import com.wwx.minishop.dao.ShopMapper;
import com.wwx.minishop.entity.*;
import com.wwx.minishop.enums.ShopStateEnum;
import com.wwx.minishop.execution.ShopExecution;
import com.wwx.minishop.repository.*;
import com.wwx.minishop.service.ProductService;
import com.wwx.minishop.service.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        PersonInfo personInfo = new PersonInfo(1, "吴文锡", "男", "wwx@springboot.com", null
                , 1, new Date(), new Date(), 0);
        personInfoRepository.saveAndFlush(personInfo);
    }

    @Test
    public void testShopCategory() {
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(8);
        ShopCategory shopCategory0 = new ShopCategory(null, "干洗店", "干洗店", 100,
                new Date(), new Date(), parent);
        ShopCategory shopCategory1 = new ShopCategory(null, "杂货铺", "杂货铺", 100,
                new Date(), new Date(), parent);
        ShopCategory shopCategory2 = new ShopCategory(null, "文具店", "文具店", 100,
                new Date(), new Date(), parent);
        ShopCategory shopCategory3 = new ShopCategory(null, "日常用品", "日常用品", 100,
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
        ShopExecution execution = shopService.getAllShop(shop);
        System.out.println("店铺个数: " + execution.getCount());
        System.out.println(execution.getShops());
    }
}


