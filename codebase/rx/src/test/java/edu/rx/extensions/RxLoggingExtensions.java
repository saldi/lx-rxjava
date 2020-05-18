package edu.rx.extensions;

import io.reactivex.SingleTransformer;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Log4j2
public class RxLoggingExtensions {

    private static final Marker BASE = MarkerManager.getMarker("BASE");
    private static final Marker ERROR = MarkerManager.getMarker("RX ERROR").addParents(BASE);
    private static final Marker INFO = MarkerManager.getMarker("RX INFO").addParents(BASE);

    public static <I> SingleTransformer<I, I> logSingleExecutionTime() {
        final var start = new AtomicLong();
        return stream -> stream.doOnSubscribe(__ -> start.set(System.currentTimeMillis()))
                               .doOnSuccess(__ -> log.info(
                                       INFO,
                                       "timeMs {}", timeMs(start)))
                               .doOnError(__ -> log.info(
                                       ERROR,
                                       "timeMs {}", timeMs(start)));
    }

    private static long timeMs(AtomicLong start) {
        return System.currentTimeMillis() - start.get();
    }
}
