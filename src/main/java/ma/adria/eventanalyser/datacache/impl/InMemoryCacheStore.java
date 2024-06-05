package ma.adria.eventanalyser.datacache.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.datacache.CacheStore;

import java.util.concurrent.TimeUnit;

/**
 * An in-memory cache store implementation using Google Guava Cache.
 *
 * @param <T> the type of the value to be stored in the cache.
 */
@Slf4j
public class InMemoryCacheStore<T> implements CacheStore<T> {
    private final Cache<String, T> cache;

    /**
     * Constructs an in-memory cache store with a specified expiry duration.
     *
     * @param expiryDuration the duration after which cache entries will expire.
     * @param timeUnit       the time unit of the expiry duration.
     */
    public InMemoryCacheStore(int expiryDuration, TimeUnit timeUnit) {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    @Override
    public T get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void put(String key, T value) {
        if (key != null && value != null) {
            cache.put(key, value);
            log.info("Record stored in {} Cache with Key {}"
                    , value.getClass().getSimpleName(), key);
        }
    }

}
