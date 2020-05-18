package edu.react.rx.observable;

import io.reactivex.disposables.Disposable;

import java.util.concurrent.atomic.AtomicBoolean;

abstract public class AbstractDisposable implements Disposable {
    private final AtomicBoolean mDisposed = new AtomicBoolean();

    @Override
    public void dispose() {
        if (mDisposed.compareAndSet(false, true)) {
            disposeActual();
        }
    }

    @Override
    public boolean isDisposed() {
        return mDisposed.get();
    }

    abstract void disposeActual();
}