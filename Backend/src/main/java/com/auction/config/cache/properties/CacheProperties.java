package com.auction.config.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Collections;
import java.util.Map;

@Data
@ConfigurationProperties("caching")
public class CacheProperties {
  private final boolean enabled;
  private final int timeToLiveMinutes;
  private final Map<String, CachePropertiesEntry> configuredCaches;

  @ConstructorBinding
  public CacheProperties(boolean enabled, int timeToLiveMinutes, Map<String, CachePropertiesEntry> configuredCaches) {
    this.enabled = enabled;
    this.timeToLiveMinutes = timeToLiveMinutes;
    this.configuredCaches = Collections.unmodifiableMap(configuredCaches);
  }
}