package edu.rx;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SimpleTest {

    private static final Logger LOGGER = LogManager.getLogger(SimpleTest.class.getName());
    @Test
    public void simple() {

        Observable<String> observable = Observable.just("Kowalski Jan");

        observable
                .subscribe((name) -> {
                    System.out.println(name);
                });

        observable
                .subscribe((name) -> {
                    System.out.println(name);
                });

        observable
                .subscribe((name) -> {
                    System.out.println(name);
                });

    }

    @Test
    public void array() {

        Observable.fromArray(1, 2, 3, 4, 5)
                  .filter(value -> value > 3)
                  .map(value -> String.valueOf(value) + " ala ma kota")
                  .subscribe(value ->
                          System.out.println(value));

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
                System.out.println("1 "+date);
            });
        });
        MILLISECONDS.sleep(5000);

        executorService.submit(() -> {
            observable.subscribe(date -> {
                System.out.println("2 "+date);
            });
        });

        MILLISECONDS.sleep(10000);


    }

    @Test
    public void threeCellsExample() throws InterruptedException {
        Observable<Long> firstCell = Observable.interval(2000, TimeUnit.MILLISECONDS);
        Observable<Long> secondCell = Observable.interval(5500, TimeUnit.MILLISECONDS);
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                  .mergeWith(firstCell)
                  .mergeWith(secondCell)
                  .buffer(500, TimeUnit.MILLISECONDS)
                  .filter((list) -> list.size() == 0)
                  .subscribe((value) -> LOGGER.info(value));
        Thread.sleep(10000);
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
                    .observeOn(scheduler)
                    .map((value) -> {
                        LOGGER.info("map1");
                        Thread.sleep(3);
                        return value;
                    })
//                    .observeOn(scheduler)
                    .subscribe((value) -> {
                        latch.countDown();
                        LOGGER.info("wartość " + value);
                    });

        });
        workService.submit(() -> {
            interval.subscribeOn(subscribeScheduler)
                    .observeOn(scheduler)
                    .map((value) -> {
                        LOGGER.info("map1");
                        return value;
                    })
                    .observeOn(scheduler)
                    .subscribe((value) -> {
                        latch.countDown();
                        LOGGER.info("wartość " + value);
                    });

        });
        workService.submit(() -> {
            interval.subscribeOn(subscribeScheduler)
                    .observeOn(scheduler)
                    .map((value) -> {
                        LOGGER.info("map1");
                        return value;
                    })
                    .observeOn(scheduler)
                    .subscribe((value) -> {
                        latch.countDown();
                        LOGGER.info("wartość " + value);
                    });

        });

        latch.await(10, TimeUnit.SECONDS);

    }

}
