//package com.auction.config.cache;
//
//import com.auction.config.cache.properties.CacheNames;
//import com.auction.config.cache.properties.CacheProperties;
//import com.auction.config.cache.properties.CachePropertiesEntry;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration(proxyBeanMethods = false)
//public class CacheConfig {
//
//  @ConditionalOnProperty(name = "caching.enabled", havingValue = "true", matchIfMissing = true)
//  @Configuration(proxyBeanMethods = false)
//  @EnableCaching
//  @EnableConfigurationProperties(CacheProperties.class)
//  static class RedisCacheConfig {
//    @Bean
//    @ConditionalOnProperty(name = "caching.type", havingValue = "redis", matchIfMissing = true)
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory, CacheProperties cacheProperties) {
//      Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
//      for (Map.Entry<String, CachePropertiesEntry> cacheEntry : cacheProperties.getConfiguredCaches().entrySet()) {
//        String cacheName = cacheEntry.getKey();
//        CachePropertiesEntry cacheConfig = cacheEntry.getValue();
//        if (cacheConfig.isEnabled()) {
//          final RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
//                  .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
//                          new GenericJackson2JsonRedisSerializer()))
//                  .disableCachingNullValues()
//                  .entryTtl(Duration.ofMinutes(cacheConfig.getTimeToLiveMinutes()));
//          cacheConfigurations.put(cacheName, configuration);
//        }
//      }
//      return RedisCacheManager.builder(connectionFactory)
//              .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(
//                      cacheProperties.getTimeToLiveMinutes())))
//              .withInitialCacheConfigurations(cacheConfigurations)
//              .enableStatistics()
//              .build();
//    }
//  }
//
//  @ConditionalOnProperty(name = "caching.enabled", havingValue = "true")
//  @Configuration(proxyBeanMethods = false)
//  @EnableCaching
//  @EnableConfigurationProperties(CacheProperties.class)
//  static class MapCacheConfig {
//    @Bean
//    @ConditionalOnProperty(name = "caching.type", havingValue = "map")
//    public CacheManager cacheManager() {
//      return new ConcurrentMapCacheManager(
//              CacheNames.USERS_CACHE
//      );
//    }
//  }
//}