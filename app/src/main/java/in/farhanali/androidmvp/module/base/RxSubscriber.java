package in.farhanali.androidmvp.module.base;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Farhan Ali
 *
 * A utility class which provides Rx subscribe and dispose methods.
 */
public class RxSubscriber {

    /**
     * Adds a subscription of passed observer to passed observable.
     *
     * @param subscription
     * @param observable
     * @param observer
     */
    public static void subscribe(
            CompositeDisposable subscription, Observable observable, DisposableObserver observer) {
        //noinspection unchecked
        subscription.add((Disposable) observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    /**
     * Reconstruct a Subscription object if it is null or unsubscribed,
     * This method is usually called from attachViewInteractor of a presenter with network call.
     *
     * @param disposable
     */
    public static void validateSubscription(Disposable disposable) {
        if (disposable == null || disposable.isDisposed()) {
            disposable = new CompositeDisposable();
        }
    }

    /**
     * Unsubscribe a subscription - usually called from detachViewInteractor of network related presenter.
     * @param disposable
     */
    public static void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

}
