package edu.rx.observable.cells;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Cell extends Observable<Integer> {

    private AtomicInteger value;

    private Function<Integer, Integer> function;

    private List<Consumer<Integer>> consumers;

    private Disposable disposable;

    {
        this.consumers = new ArrayList<>();
        this.value = new AtomicInteger(0);
    }

    public Cell() {

        this.function = i -> {
            return i;
        };
    }

    public Cell(Function<Integer, Integer> function) {
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        Consumer<Integer> consumer = observer::onNext;
        AbstractDisposable disposable = new AbstractDisposable() {
            @Override
            void disposeActual() {
                consumers.remove(consumer);
            }
        };
        observer.onSubscribe(disposable);
        consumers.add(consumer);

        if (!disposable.isDisposed()) {
            observer.onNext(value.get());
        }

    }

    public void removeSubscribe(){
        this.disposable.dispose();
        this.disposable = null;
    }

    public Integer getValue() {
        return value.get();
    }

    public void update(Integer i) {
        this.value.set(i);
        for (Consumer<Integer> consumer : consumers) {
            consumer.accept(i);
        }
    }

    public void subscribeOn(Cell cell) {
        this.disposable = cell.map(this.function)
                              .subscribe(this::update);
    }

}
