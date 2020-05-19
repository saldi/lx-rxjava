package edu.rx.testing;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class TimeTesting {

    @Test
    public void timeTest() {

        TestScheduler testScheduler = new TestScheduler();
        TestObserver<Long> testObserver = longTimeService()
                .timeout(1000, TimeUnit.MILLISECONDS, testScheduler)
                .doOnError(t -> log.error(t.getMessage()))
                .retry(5)
                .onErrorReturn(t -> -1L)
                .test();

        testObserver.assertNoErrors();
        testObserver.assertNoValues();
        testScheduler.advanceTimeBy(5_999, TimeUnit.MILLISECONDS);
        testObserver.assertNoErrors();
        testObserver.assertNoValues();
        testScheduler.advanceTimeBy(2, TimeUnit.MILLISECONDS);
        testObserver.assertValue(-1L);


    }


    private Observable<Long> longTimeService() {
        return Observable.timer(1, TimeUnit.MINUTES);
    }


}
