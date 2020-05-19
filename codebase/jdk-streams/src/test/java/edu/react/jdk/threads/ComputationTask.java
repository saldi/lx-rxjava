package edu.react.jdk.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ComputationTask extends RecursiveTask<Double> {

    private double[] arr;
    private static final int THRESHOLD = 50_000_000;

    public ComputationTask(double[] arr) {
        this.arr = arr;
    }

    @Override
    protected Double compute() {
        if (arr.length > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                               .stream()
                               .mapToDouble(ForkJoinTask::join)
                               .sum();
        } else {
            return processing(arr);
        }
    }

    private Collection<ComputationTask> createSubtasks() {
        List<ComputationTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new ComputationTask(
                Arrays.copyOfRange(arr, 0, arr.length / 2)));
        dividedTasks.add(new ComputationTask(
                Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        return dividedTasks;
    }

    private Double processing(double[] arr) {
        return Arrays.stream(arr)
                     .filter(a -> a > 10 && a < 27)
                     .map(a -> a * 10)
                     .sum();
    }
}