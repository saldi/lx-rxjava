package edu.saldcomp.hot;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subscribers.DefaultSubscriber;
import org.reactivestreams.Subscriber;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
