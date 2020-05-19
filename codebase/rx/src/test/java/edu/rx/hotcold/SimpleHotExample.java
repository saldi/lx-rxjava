package edu.rx.hotcold;

import io.reactivex.BackpressureStrategy;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class SimpleHotExample {

    @Test
    public void simpleExample() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        var observable = PublishSubject.<Integer>create();
        observable
                .toFlowable(BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.from(executorService), false, 1)
                .subscribe(
                        ComputeFunction::compute,
                        (t) -> log.error(t.getMessage())
                );
        IntStream.range(0, 1000000)
                 .forEach(i -> observable.onNext(i));
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    public interface ComputeFunction {

        static void compute(Integer v) {
            try {
                log.info("compute integer v: " + v);
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
