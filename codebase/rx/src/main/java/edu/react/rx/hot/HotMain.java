package edu.react.rx.hot;

import io.reactivex.BackpressureStrategy;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class HotMain {

    public static void main(String[] args) throws InterruptedException {

        var observable = PublishSubject.<Integer>create();
        observable
                .toFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.from(Executors.newFixedThreadPool(10)), false, 1)
                .subscribe(
                        ComputeFunction::compute,
                        (t) -> System.out.println(t)
                );

        IntStream.range(0, 1000000)
                 .forEach(i -> observable.onNext(i));

        Thread.sleep(40000);

    }

    public interface ComputeFunction {
        static void compute(Integer v) {
            try {
                System.out.println("compute integer v: " + v);
                Thread.sleep(0);
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
