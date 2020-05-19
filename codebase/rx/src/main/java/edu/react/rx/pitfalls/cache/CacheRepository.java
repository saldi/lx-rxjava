package edu.react.rx.pitfalls.cache;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface CacheRepository {

    Maybe<Location> load(String key);

    Completable save(String key, Location location);

}
