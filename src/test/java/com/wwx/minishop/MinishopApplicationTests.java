package com.wwx.minishop;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.dao.ProductCategoryMapper;
import com.wwx.minishop.dao.ProductMapper;
import com.wwx.minishop.dao.ShopCategoryMapper;
import com.wwx.minishop.dao.ShopMapper;
import com.wwx.minishop.entity.*;
import com.wwx.minishop.repository.*;
import com.wwx.minishop.service.ProductService;
import com.wwx.minishop.service.ShopCategoryService;
import com.wwx.minishop.service.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

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

    @Autowired
    RabbitTemplate rabbitTemplate;



    @Test
    public void testRabbitMqHandler(){
        /*Map<String,Object> map = new HashMap<>();
        map.put("shop",new Shop());
        map.put("imageHolder",new ImageHolder(null,null));*/
    }

    @Test
    public void testRabbitAdmin(){
        //参数：1、交换器名 2、是否持久化 3、是否自动删除
        //DirectExchange exchange = new DirectExchange("shop");
        /*DirectExchange exchange = new DirectExchange("product");
        rabbitAdmin.declareExchange(exchange);*/
        //参数：1、队列名 2、是否持久化
        /*rabbitAdmin.declareQueue(new Queue("add.shop",true));
        rabbitAdmin.declareQueue(new Queue("modify.shop",true));
        rabbitAdmin.declareQueue(new Queue("add.product",true));
        rabbitAdmin.declareQueue(new Queue("modify.product",true));
        System.out.println("创建完成");*/
        //rabbitAdmin.deleteQueue("add.shop");
        //参数：1.绑定的队列名 2、绑定类别 3、交换器 4、指定路由值 5、map类别的参数
        /*rabbitAdmin.declareBinding(new Binding("add.shop",Binding.DestinationType.QUEUE,
                "shop","add",null));
        rabbitAdmin.declareBinding(new Binding("modify.shop",Binding.DestinationType.QUEUE,
                "shop","modify",null));
        rabbitAdmin.declareBinding(new Binding("add.product",Binding.DestinationType.QUEUE,
                "product","add",null));
        rabbitAdmin.declareBinding(new Binding("modify.product",Binding.DestinationType.QUEUE,
                "product","modify",null));*/
    }

    @Test
    public void testsRabbit(){
        String exchange = "shop";
        String routingKey = "add";
        Shop object = new Shop();
        object.setShopId(1);
        rabbitTemplate.convertAndSend(exchange,routingKey,object);
    }

    @Test
    public void receive(){
        Object o = rabbitTemplate.receiveAndConvert("add", 200);
        System.out.println(o.getClass());
        System.out.println(o);
    }

    /*@Qualifier("productCacheManager")//指定缓存管理器
    @Autowired
    RedisCacheManager productCacheManager;*/

    @Autowired
    LocalAuthRepository localAuthRepository;

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

    @Autowired
    ShopCategoryService shopCategoryService;

    @Test
    public void test01()  {
        /*List<Shop> list = shopService.findShopListWithShopCategory(9);
        System.out.println(list);*/
        ShopCategory category = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(1);
        category.setParent(parent);
        List<ShopCategory> allShopCategory = shopCategoryService.findShopCategoryWithParentId(category);
        System.out.println(allShopCategory.size());
        System.out.println(allShopCategory);
        /*ShopCategory shopCategoryById = shopCategoryService.findShopCategoryById(14);
        System.out.println(shopCategoryById);*/
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
        /*ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(8);*/
        ShopCategory shopCategory0 = new ShopCategory(null, "美食饮品", "美食饮品", 0,
                new Date(), new Date(), null);
        ShopCategory shopCategory1 = new ShopCategory(null, "二手市场", "二手市场", 0,
                new Date(), new Date(), null);
        ShopCategory shopCategory2 = new ShopCategory(null, "租赁市场", "租赁市场", 0,
                new Date(), new Date(), null);
        ShopCategory shopCategory3 = new ShopCategory(null, "休闲娱乐", "休闲娱乐", 0,
                new Date(), new Date(), null);
        ShopCategory shopCategory4 = new ShopCategory(null, "美容美发", "美容美发", 0,
                new Date(), new Date(), null);
        ShopCategory shopCategory5 = new ShopCategory(null, "培训教育", "培训教育", 0,
                new Date(), new Date(), null);
        ShopCategory shopCategory6 = new ShopCategory(null, "运动健身", "运动健身", 0,
                new Date(), new Date(), null);
        ShopCategory shopCategory7 = new ShopCategory(null, "其他", "其他", 0,
                new Date(), new Date(), null);


        List<ShopCategory> shopCategoryArrayList = new ArrayList<>();
        shopCategoryArrayList.add(shopCategory0);
        shopCategoryArrayList.add(shopCategory1);
        shopCategoryArrayList.add(shopCategory2);
        shopCategoryArrayList.add(shopCategory3);
        shopCategoryArrayList.add(shopCategory4);
        shopCategoryArrayList.add(shopCategory5);
        shopCategoryArrayList.add(shopCategory6);
        shopCategoryArrayList.add(shopCategory7);

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


