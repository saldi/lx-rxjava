package edu.react.rx.hotcold;

import io.reactivex.BackpressureStrategy;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class HotMain {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        var observable = PublishSubject.<Integer>create();
        observable
                .toFlowable(BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.from(executorService), false, 1)
                .subscribe(
                        ComputeFunction::compute,
                        (t) -> System.out.println(t)
                );
        IntStream.range(0, 1000000)
                 .forEach(i -> observable.onNext(i));
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    public interface ComputeFunction {

        static void compute(Integer v) {
            try {
                System.out.println("compute integer v: " + v);
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        static void compute(List<Integer> v) {
            try {
                System.out.println("compute integer v: " + v);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
