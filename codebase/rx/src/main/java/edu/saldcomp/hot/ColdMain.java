package edu.saldcomp.hot;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ColdMain {

    public static void main(String[] args) throws InterruptedException {

        Observable.range(1, 1_000_000)
                  .delay(100, TimeUnit.MILLISECONDS)
                  .observeOn(Schedulers.computation())
                  .subscribe(ColdMain::compute);

    Thread.sleep(1000*30);

    }


    private static Integer compute(Integer i) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(200);
        System.out.println(i);
        return i*100;
    }





}
