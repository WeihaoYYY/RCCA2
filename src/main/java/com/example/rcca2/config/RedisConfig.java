package com.example.rcca2.config;

import com.example.rcca2.Utils.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;

@Configuration
public class RedisConfig {
    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory)
    {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }


    @Bean
    @ConditionalOnProperty(name = "spring.redis.host")
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//
////    @Value("${spring.redis.host}")
////    private String redisHost;
////
////    @PostConstruct
////    public void checkRedisConnection() {
////        try {
////            RedisConnection connection = redisConnectionFactory.getConnection(); // 使用实例调用 getConnection()
////            connection.ping();
////            System.out.println("Redis连接成功");
////        } catch (Exception e) {
////            System.err.println("Redis连接失败: " + e.getMessage());
////        }
////    }

}