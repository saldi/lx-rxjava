package edu.react.rx.pitfalls.cache;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class InMemoryCacheRepository implements CacheRepository {

    private ConcurrentHashMap<String, Location> cache = new ConcurrentHashMap<>();

    @Override
    public Maybe<Location> load(String key) {
        var location = cache.get(key);
        log.info("Get location from cache {}", location);
        if (location == null) {
            return Maybe.empty();
        } else {
            Maybe.just(cache.get(key));
        }
        return Maybe.empty();
    }

    @Override
    public Completable save(String key, Location location) {
        return Completable.fromCallable(() -> cache.put(key, location));
    }
}
