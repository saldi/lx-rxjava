package edu.rx;

import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class Interval {


    @Test
    public void intervalTest() throws InterruptedException {
        var observable = Observable.interval(2, TimeUnit.SECONDS);
        observable.subscribe(log::info);
        Thread.sleep(1000 * 2);
        observable.subscribe(log::info);

        Thread.sleep(1000 * 50);

    }

}
