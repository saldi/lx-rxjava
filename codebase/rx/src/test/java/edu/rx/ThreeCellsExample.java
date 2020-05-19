package edu.rx;

import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class ThreeCellsExample {

    @Test
    public void threeCellsExample() throws InterruptedException {
        Observable<Long> firstCell = Observable.interval(2000, TimeUnit.MILLISECONDS);
        Observable<Long> secondCell = Observable.interval(5500, TimeUnit.MILLISECONDS);
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                  .mergeWith(firstCell)
                  .mergeWith(secondCell)
                  .blockingSubscribe((value) -> log.info(value));
    }
}
