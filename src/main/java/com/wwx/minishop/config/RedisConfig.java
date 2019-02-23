package com.wwx.minishop.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwx.minishop.entity.*;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;


@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    public class MyJackson2JsonRedisSerializer<T> extends Jackson2JsonRedisSerializer{

        MyJackson2JsonRedisSerializer(Class type) {
            super(type);
        }
    }

    private RedisCacheConfiguration commons(Jackson2JsonRedisSerializer<Object> redisSerializer){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        redisSerializer.setObjectMapper(objectMapper);

        return  RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));

    }

    @SuppressWarnings("unchecked")
    @Primary
    @Bean
    public RedisCacheManager primaryCacheManager(RedisConnectionFactory connectionFactory){
        MyJackson2JsonRedisSerializer<Object> redisSerializer = new MyJackson2JsonRedisSerializer<>(Object.class);

        RedisCacheConfiguration cacheConfiguration = commons(redisSerializer);

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();

    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisCacheManager productCacheManager(RedisConnectionFactory connectionFactory) {

        MyJackson2JsonRedisSerializer<Product> redisSerializer = new MyJackson2JsonRedisSerializer<>(Product.class);

        RedisCacheConfiguration cacheConfiguration = commons(redisSerializer);

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisCacheManager shopCacheManager(RedisConnectionFactory connectionFactory){

        MyJackson2JsonRedisSerializer<Shop> redisSerializer = new MyJackson2JsonRedisSerializer<>(Shop.class);

        RedisCacheConfiguration cacheConfiguration = commons(redisSerializer);

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisCacheManager shopCategoryCacheManager(RedisConnectionFactory connectionFactory){

        MyJackson2JsonRedisSerializer<ShopCategory> redisSerializer = new MyJackson2JsonRedisSerializer<>(ShopCategory.class);

        RedisCacheConfiguration cacheConfiguration = commons(redisSerializer);

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisCacheManager productCategoryCacheManager(RedisConnectionFactory connectionFactory){

        MyJackson2JsonRedisSerializer<ProductCategory> redisSerializer = new MyJackson2JsonRedisSerializer<>(ProductCategory.class);

        RedisCacheConfiguration cacheConfiguration = commons(redisSerializer);

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisCacheManager personInfoCacheManager(RedisConnectionFactory connectionFactory){

        MyJackson2JsonRedisSerializer<PersonInfo> redisSerializer = new MyJackson2JsonRedisSerializer<>(PersonInfo.class);

        RedisCacheConfiguration cacheConfiguration = commons(redisSerializer);

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisCacheManager localAuthCacheManager(RedisConnectionFactory connectionFactory){

        MyJackson2JsonRedisSerializer<LocalAuth> redisSerializer = new MyJackson2JsonRedisSerializer<>(LocalAuth.class);

        RedisCacheConfiguration cacheConfiguration = commons(redisSerializer);

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();
    }


}
