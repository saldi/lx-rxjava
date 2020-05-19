package edu.rx.hotcold;

import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class SimpleCold {

    @Test
    public void simple() throws InterruptedException {
        Observable.range(1, 1_000_000)
                  .delay(100, TimeUnit.MILLISECONDS)
                  .blockingSubscribe(SimpleCold::compute);
    }


    private static Integer compute(Integer i) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(200);
        log.info(i);
        return i * 100;
    }


}
