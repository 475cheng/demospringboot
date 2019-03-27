package com.bitauto.ep.fx.webapi.configuration.cache;


import com.alibaba.fastjson.parser.ParserConfig;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: shenyc
 * @Date: 2019/3/25 14:05
 * @Description:　@Description: 使用情况 ：@Cacheable(key = "#name", cacheManager = "redisCacheManager", cacheNames = "qwer2")    eh中 cacheNames 为ehcache.xml中的name
 */
@Configuration
@EnableCaching
public class CacheConfig {

    //@Value("${com.bitauto.ep.fx.rediskeyspace}")
    private String redisKeySpace="";

    @Bean(name = "ehCacheManager")
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

    @Bean("redisCacheManager")
    @Primary
    public RedisCacheManager redisCacheManager(RedisTemplate<Object, Object> redisTemplate) {

        FastJsonRedisSerializers<Object> stringSerializer = new FastJsonRedisSerializers(Object.class);
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        RedisCachePrefix redisCachePrefix = new RedisCachePrefix() {
            @Override
            public byte[] prefix(String s) {
                return redisKeySpace.concat(":").getBytes();
            }
        };
        cacheManager.setCachePrefix(redisCachePrefix);
        cacheManager.setUsePrefix(true);
        this.redisCacheManagerCustomizer().customize(cacheManager);
        return cacheManager;
    }

    @Bean
    public RedisCacheCustomizer redisCacheManagerCustomizer() {
        return new RedisCacheCustomizer();
    }

    private static class RedisCacheCustomizer
            implements CacheManagerCustomizer<RedisCacheManager> {
        /**
         * CacheManager缓存自定义初始化比较早，尽量不要@autowired 其他spring 组件
         */
        @Override
        public void customize(RedisCacheManager cacheManager) {
            // 自定义缓存名对应的过期时间
            Map<String, Long> expires = ImmutableMap.<String, Long>builder()
                    .put("eee", TimeUnit.MINUTES.toSeconds(15))
                    .put("rrrr", TimeUnit.MINUTES.toSeconds(30))
                    .put("fff", TimeUnit.MINUTES.toSeconds(60))
                    .put("aaaa", TimeUnit.MINUTES.toSeconds(180)).build();
            // spring cache是根据cache name查找缓存过期时长的，如果找不到，则使用默认值
            cacheManager.setDefaultExpiration(TimeUnit.MINUTES.toSeconds(30));
            cacheManager.setExpires(expires);
        }
    }
}
