package com.felix.base.arch;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * https://github.com/listenzz/RxCommand/blob/master/rxcommand/src/main/java/com/shundaojia/rxcommand/RxCommand.java
 * @author yiyang
 */
public class RxCommand<T> {

    public interface Func<T, R> {
        R apply(@Nullable T t) throws Exception;
    }

    public static <T> RxCommand<T> create(Func<Object, Observable<T>> function) {
        return new RxCommand<>(function);
    }

    public static <T> RxCommand<T> create(Observable<Boolean> enabled, Func<Object, Observable<T>> function) {
        return new RxCommand<>(enabled, function);
    }

    private final Func<Object, Observable<T>> mFunc;

    private final Subject<Observable<T>> mAddedExecutionObservableSubject;

    private final Subject<Boolean> mAllowsConcurrentExecutionSubject;

    /**
     * see {@link #executionObservables()}
     */
    private final Observable<Observable<T>> mExecutionObservables;

    /**
     * see {@link #switchToLatest()}
     */
    private final Observable<T> mLatestObservable;

    /**
     * see {@link #errors()}
     */
    private final Observable<Throwable> mErrors;

    /**
     * see {@link #executing()}
     */
    private final Observable<Boolean> mExecuting;

    private final ConnectableObservable<Boolean> mImmediateEnabled;

    /**
     * see {@link #enabled()}
     */
    private final Observable<Boolean> mEnabled;

    /**
     * see {@link #allowsConcurrentExecution()}
     */
    private volatile boolean mAllowsConcurrentExecution;

    /**
     * create a command that is conditionally enabled.
     *
     * @param enabledObservable An observable of Booleans which indicate whether the command should
     *                          be enabled. {@link #enabled()} will be based on the latest value sent
     *                          from this observable. Before any values are sent, {@link #enabled()} will
     *                          default to true. This argument may be null.
     * @param func              - A function which will map each input value (passed to {@link #execute(Object)})
     *                          to a observable of work. The returned observable will be multicasted
     *                          to a replay subject, sent on {@link #executionObservables()}, then
     *                          subscribed to synchronously. Neither the function nor the
     *                          returned observable may be null.
     */
    public RxCommand(@Nullable Observable<Boolean> enabledObservable, @NonNull Func<Object, Observable<T>> func) {

        mAddedExecutionObservableSubject = PublishSubject.create();
        mAllowsConcurrentExecutionSubject = PublishSubject.create();
        mFunc =func;

        mExecutionObservables = mAddedExecutionObservableSubject
                .map(observable -> observable.onErrorResumeNext(Observable.<T>empty()))
                .observeOn(AndroidSchedulers.mainThread());

        mLatestObservable = Observable
                .switchOnNext(mExecutionObservables)
                .publish()
                .autoConnect();

        mErrors = mAddedExecutionObservableSubject
                .flatMap(observable -> observable
                        .materialize()
                        .filter(Notification::isOnError)
                        .map(Notification::getError)
                )
                .observeOn(AndroidSchedulers.mainThread())
                // if someone subscribes to `errors` _after_ an execution
                // has started, it should still receive any error from that execution.
                .publish()
                .autoConnect();

        Observable<Boolean> immediateExecuting = mAddedExecutionObservableSubject
                .flatMap(observable -> observable
                        .ignoreElements()
                        .toMaybe()
                        .toObservable()
                        .onErrorResumeNext(Observable.<T>empty())
                        .concatWith(Observable.just(-1))
                        .startWith(1)
                        .cast(Integer.class))
                .scan(0, (running, next) -> running + next)
                .map(count -> count > 0)
                .startWith(false);

        mExecuting = immediateExecuting
                .observeOn(AndroidSchedulers.mainThread())
                // This is useful before the first value arrives on the main thread.
                .startWith(false)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect();

        Observable<Boolean> moreExecutionsAllowed = Observable
                .combineLatest(
                        mAllowsConcurrentExecutionSubject.startWith(false),
                        immediateExecuting,
                        (allowedConcurrent, executing) -> allowedConcurrent || !executing)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect();

        if (enabledObservable == null) {
            enabledObservable = Observable.just(true);
        }

        mImmediateEnabled = Observable
                .combineLatest(enabledObservable, moreExecutionsAllowed, (enabled, allowed) -> enabled && allowed)
                .distinctUntilChanged()
                .replay(1);
        // since `mImmediateEnabled` is not export, we must trigger this stream manually
        mImmediateEnabled.connect();

        mEnabled = Observable
                .concat(mImmediateEnabled.take(1),
                        mImmediateEnabled.skip(1).observeOn(AndroidSchedulers.mainThread()))
                .distinctUntilChanged()
                .replay(1)
                .autoConnect();
    }

    /**
     * Call {@link #RxCommand(Observable, Func)} with a null `enabledObservable`.
     *
     * @param func the factory creating observable
     */
    public RxCommand(Func<Object, Observable<T>> func) {
        this(null, func);
    }

    /**
     * see {@link #allowsConcurrentExecution()}
     *
     * @param allows whether allows concurrent execution
     */
    public final void setAllowsConcurrentExecution(boolean allows) {
        mAllowsConcurrentExecution = allows;
        mAllowsConcurrentExecutionSubject.onNext(allows);
    }

    /**
     * An observable of the observables returned by successful invocations of {@link #execute(Object)}
     * (i.e., while the receiver is {@link #enabled()}).
     * <p>
     * Errors will be automatically caught upon the inner observables, and sent upon
     * {@link #errors()} instead. If you _want_ to receive inner errors, use {@link #execute(Object)} or
     * {@link Observable#materialize()}
     * <p>
     * Only executions that begin _after_ subscription will be sent upon this
     * observable. All inner observables will arrive upon the main thread.
     */
    public Observable<Observable<T>> executionObservables() {
        return mExecutionObservables;
    }

    /**
     * An observable of whether this command is currently executing.
     * <p>
     * This will send true whenever {@link #execute(Object)} is invoked and the created observable has
     * not yet terminated. Once all executions have terminated, this observable will
     * send false.
     * <p>
     * This observable will send its current value upon subscription, and then all
     * future values on the main thread.
     */
    public Observable<Boolean> executing() {
        return mExecuting;
    }

    /**
     * An observable of whether this command is able to execute.
     * This will send false if:
     * <p>
     * - The command was created with an `enabledObservable`, and false is sent upon that
     * observable, or
     * - {@link #allowsConcurrentExecution()} is false and the command has started executing.
     * <p>
     * Once the above conditions are no longer met, the observable will send true.
     * <p>
     * This observable will send its current value upon subscription, and then all
     * future values on the main thread.
     */
    public Observable<Boolean> enabled() {
        return mEnabled;
    }

    /**
     * Forwards any errors that occur within observables returned by {@link #execute(Object)}.
     * <p>
     * When an error occurs on a observable returned from {@link #execute(Object)}, this observable will
     * send the associated {@link Throwable} value as a `next` event (since an `error` event
     * would terminate the stream).
     * <p>
     * After subscription, this observable will send all future errors on the main
     * thread.
     */
    public Observable<Throwable> errors() {
        return mErrors;
    }

    /**
     * Whether the command allows multiple executions to proceed concurrently.
     * <p>
     * The default value for this property is false.
     */
    public boolean allowsConcurrentExecution() {
        return mAllowsConcurrentExecution;
    }

    /**
     * switch to the latest observable of observables send by {@link #executionObservables()}
     *
     * @return the latest observable
     */
    public Observable<T> switchToLatest() {
        return mLatestObservable;
    }

    /**
     * If the receiver is enabled, this method will:
     * <p>
     * 1. Invoke the `func` given at the time of creation.
     * 2. Multicast the returned observable.
     * 3. Send the multicasted observable on {@link #executionObservables()}.
     * 4. Subscribe (connect) to the original observable on the main thread.
     *
     * @param input The input value to pass to the receiver's `func`. This may be null.
     * @return the multicasted observable, after subscription. If the receiver is not
     * enabled, returns a observable that will send an error.
     */
    @MainThread
    public final Observable<T> execute(@Nullable Object input) {
        boolean enabled = mImmediateEnabled.blockingFirst();
        if (!enabled) {
            return Observable.error(new IllegalStateException("The command is disabled and cannot be executed"));
        }
        try {
            Observable<T> observable = mFunc.apply(input);
            if (observable == null) {
                throw new RuntimeException(String.format("null Observable returned from observable func for value %s", input));
            }

            // This means that `executing` and `enabled` will send updated values before
            // the observable actually starts performing work.
            final ConnectableObservable<T> connection = observable
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .replay();

            mAddedExecutionObservableSubject.onNext(connection);
            connection.connect();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);
        }
    }

    /**
     * 对{@link #execute}的改造，返回的是一个Observable，调用dispose()后会将事件发送到mAddedExecutionObservableSubject
     * @param input
     * @return
     */
    @MainThread
    public final Observable<T> executeCancelable(@Nullable Object input) {
        boolean enabled = mImmediateEnabled.blockingFirst();
        if (!enabled) {
            return Observable.error(new IllegalStateException("The command is disabled and cannot be executed"));
        }
        try {
            Observable<T> observable = mFunc.apply(input);
            if (observable == null) {
                throw new RuntimeException(String.format("null Observable returned from observable func for value %s", input));
            }

            // This means that `executing` and `enabled` will send updated values before
            // the observable actually starts performing work.
            /*final ConnectableObservable<T> connection = observable
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .replay();

            mAddedExecutionObservableSubject.onNext(connection);
            connection.connect();*/
            PublishSubject<T> objectPublishSubject = PublishSubject.create();
            mAddedExecutionObservableSubject.onNext(objectPublishSubject);
            return observable.doOnEach(objectPublishSubject).doOnDispose(new Action() {
                @Override
                public void run() throws Exception {
                    objectPublishSubject.onComplete();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);
        }
    }
}
