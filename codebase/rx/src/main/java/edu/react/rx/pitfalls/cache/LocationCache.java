package edu.react.rx.pitfalls.cache;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.config;
import static org.asynchttpclient.Dsl.get;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.react.rx.Functions;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.log4j.Log4j2;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.extras.rxjava2.RxHttpClient;

@Log4j2
public class LocationCache {

    public static final String USERS_URL = "http://localhost:8080/users";
    public static final String LOCATIONS_URL = "http://localhost:9000/locations/";

    AsyncHttpClient asyncHttpClient = asyncHttpClient(config().build());
    RxHttpClient rxHttpClient = RxHttpClient.create(asyncHttpClient);
    ExecutorService workers = Executors.newFixedThreadPool(10);
    ObjectMapper objectMapper = new ObjectMapper();

    CacheRepository cacheRepository = new InMemoryCacheRepository();

    public static void main(String[] args) {
        new LocationCache().start();
    }

    private void start() {

        List<User> users = rxHttpClient
                .prepare(get(USERS_URL).addHeader("TraceId", Functions.generateTraceId()).build())
                .map(response -> {
                    return objectMapper
                            .readValue(response.getResponseBody(), new TypeReference<List<User>>() {
                                    }
                            );
                }).blockingGet();

        Observable.fromIterable(users)
                  .subscribeOn(Schedulers.from(workers))
                  .subscribe(user -> {
                      String divisionNumber = user.divisionNumber;
                      Thread.sleep(1000);
                      cacheRepository.load(divisionNumber)
                                     .switchIfEmpty(Maybe.defer(() ->
                                             fetchDataFromSource(divisionNumber)
                                     )).subscribe(
                              location -> log.info(location.locationName)
                      );
                  });
    }


    private Maybe<Location> fetchDataFromSource(String divisionNumber) {
        return loadLocation(divisionNumber)
                .doOnSuccess(location -> {
                    cacheRepository.save(divisionNumber, location);
                })
                .doOnSuccess(location -> {
                    log.info("retrieve");
                });
    }

    private Maybe<Location> loadLocation(String divisionNumber) {
        String url = LOCATIONS_URL + divisionNumber;
        return rxHttpClient.prepare(
                get(url).addHeader("TraceId", Functions.generateTraceId()).build())
                           .map(locResponse -> {
                               Location location = objectMapper
                                       .readValue(locResponse.getResponseBody(),
                                               Location.class);
                               log.info("load location {}", location.locationName);
                               return location;
                           });
    }
}