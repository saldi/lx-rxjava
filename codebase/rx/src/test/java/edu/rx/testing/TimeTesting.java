package edu.rx.testing;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TimeTesting {

    private Observable<Long> longTimeService() {
        return Observable.timer(1, TimeUnit.MINUTES);
    }


}
