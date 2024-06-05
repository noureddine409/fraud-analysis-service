package ma.adria.eventanalyser.datacache;

/**
 * Interface representing a cache store for storing and retrieving data.
 *
 * @param <T> the type of the value to be stored in the cache.
 */
public interface CacheStore<T> {

    /**
     * Retrieves an item from the cache based on the given key.
     *
     * @param key the key identifying the item to be retrieved.
     * @return the item associated with the specified key, or null if the item is not found in the cache.
     */
    T get(String key);

    /**
     * Stores an item in the cache with the given key.
     *
     * @param key   the key identifying the item to be stored.
     * @param value the item to be stored in the cache.
     */
    void put(String key, T value);
}
