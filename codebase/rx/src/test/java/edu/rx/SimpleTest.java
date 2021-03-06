package edu.rx;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

@Log4j2
public class SimpleTest {

    private static final Logger LOGGER = LogManager.getLogger(SimpleTest.class.getName());

    @Test
    public void simple() {
        Observable<String> observable = Observable.just("Kowalski Jan");
        observable.map(s -> s.toUpperCase())
                  .subscribe(s -> log.info(s));

    }

    @Test
    public void array() {
        Observable.fromArray(1, 2, 3, 4, 5);
    }

    @Test
    public void mapExample() {

        Observable<String> stream = Observable.just("Jan Kowalski");
        stream.map(str -> str.toUpperCase())
              .map(str -> null)
              .subscribe(str -> log.info(str), throwable -> log.error(throwable));

    }


    @Test
    public void emitter() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Observable<Date> observable = Observable.create(emitter -> {
            for (int i = 0; i < 10; i++) {
                emitter.onNext(new Date());
                Thread.sleep(1000);
            }
        });

        executorService.submit(() -> {
            observable.subscribe(date -> {
                log.info("1 " + date);
            });
        });
        MILLISECONDS.sleep(5000);

        executorService.submit(() -> {
            observable.subscribe(date -> {
                log.info("2 " + date);
            });
        });
        MILLISECONDS.sleep(10000);
    }


    @Test
    public void threads() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(3);
        Observable<Long> interval = Observable.interval(100, TimeUnit.MILLISECONDS);

        ExecutorService workService = Executors.newFixedThreadPool(5);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Scheduler scheduler = Schedulers.from(executorService);
        Scheduler subscribeScheduler = Schedulers.from(Executors.newFixedThreadPool(5));

        workService.submit(() -> {
            interval.subscribeOn(subscribeScheduler)
                    .map((value) -> {
                        log.info("map1");
                        Thread.sleep(3);
                        return value;
                    })
                    .subscribe((value) -> {
                        latch.countDown();
                        log.info("wartość " + value);
                    });

        });
        workService.submit(() -> {
            interval.subscribeOn(subscribeScheduler)
                    .map((value) -> {
                        log.info("map1");
                        return value;
                    })
                    .subscribe((value) -> {
                        latch.countDown();
                        log.info("wartość " + value);
                    });

        });
        workService.submit(() -> {
            interval.subscribeOn(subscribeScheduler)
                    .map((value) -> {
                        log.info("map1");
                        return value;
                    })
                    .subscribe((value) -> {
                        latch.countDown();
                        log.info("wartość " + value);
                    });

        });

        latch.await(10, TimeUnit.SECONDS);

    }

}
