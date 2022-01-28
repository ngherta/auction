package com.auction.helper;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserSessionCache {

  private final LoadingCache<String, String> userBySession =
          CacheBuilder.newBuilder()
                  .build(new CacheLoader<>() {
                    @Override
                    public String load(String key) {
                      return null;
                    }
                  });

  public void put(String username, String sessionId) {
    userBySession.put(username, sessionId);
  }

  public void remove(String sessionId) {
    userBySession.invalidate(sessionId);
  }

  public Set<Long> getActiveUsers() {
    Set<String> active = Sets.newTreeSet();
    active.addAll(userBySession.asMap().values());
    return active.stream()
            .map(Long::valueOf)
            .collect(Collectors.toSet());
  }

  public Map<String, String> getActiveUsersMap() {
    return userBySession.asMap();
  }
}
