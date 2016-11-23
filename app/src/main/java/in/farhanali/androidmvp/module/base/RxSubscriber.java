package in.farhanali.androidmvp.module.base;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Farhan Ali
 *
 * A utility class which provides Rx subscribe and unsubscribe methods.
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
            CompositeSubscription subscription, Observable observable, Observer observer) {
        //noinspection unchecked
        subscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer));
    }

    /**
     * Reconstruct a Subscription object if it is null or unsubscribed,
     * This method is usually called from attachViewInteractor of a presenter with network call.
     *
     * @param subscription
     */
    public static void validateSubscription(Subscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            subscription = new CompositeSubscription();
        }
    }

    /**
     * Unsubscribe a subscription - usually called from detachViewInteractor of network related presenter.
     * @param subscription
     */
    public static void unsubscribe(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

}
