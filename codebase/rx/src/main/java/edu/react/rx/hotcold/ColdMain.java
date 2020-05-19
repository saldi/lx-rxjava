package edu.react.rx.hotcold;

import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ColdMain {

    public static void main(String[] args) throws InterruptedException {
        Observable.range(1, 1_000_000)
                  .delay(100, TimeUnit.MILLISECONDS)
                  .subscribe(ColdMain::compute);
        Thread.sleep(1000 * 30);
    }


    private static Integer compute(Integer i) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(200);
        log.info(i);
        return i * 100;
    }


}
