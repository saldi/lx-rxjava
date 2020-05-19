package edu.react.jdk.threads;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Log4j2
public class TimeExecutionTest {

    private double[] values = new double[200_000_000];

    @BeforeEach
    void setUp() {
        Arrays.fill(values, 1);
    }

    void measureTimeExecution(Runnable procedure) {
        try {
            long l = System.currentTimeMillis();
            procedure.run();
            log.info("Time {} ms", System.currentTimeMillis() - l);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Test
    public void oneThreadComputation() {
        measureTimeExecution(() -> {
            double sum = Arrays.stream(values).sum();
            log.info(sum);
        });
    }

    @Test
    public void forkJoin() {

        measureTimeExecution(() -> {
            ForkJoinPool pool = new ForkJoinPool();
            ComputationTask task = new ComputationTask(values);
            pool.execute(task);
            log.info(task.join());
        });

    }

}
