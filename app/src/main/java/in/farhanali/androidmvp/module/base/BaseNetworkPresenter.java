package in.farhanali.androidmvp.module.base;

import in.farhanali.androidmvp.data.api.ApiObserver;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Farhan Ali
 *
 * An extension of BasePresenter with facility to subscribe and dispose rx.subscription.
 * Presenters that are going to interact with api/network can extend this.
 */
public abstract class BaseNetworkPresenter<T extends ViewInteractor> extends BasePresenter<T> {

    protected CompositeDisposable subscription = new CompositeDisposable();

    @Override
    public void attachViewInteractor(T viewInteractor) {
        RxSubscriber.validateSubscription(subscription);
        super.attachViewInteractor(viewInteractor);
    }

    @Override
    public void detachViewInteractor() {
        RxSubscriber.dispose(subscription);
        super.detachViewInteractor();
    }

    protected void subscribeForNetwork(Observable resultObservable, ApiObserver apiObserver) {
        RxSubscriber.subscribe(subscription, resultObservable, apiObserver);
    }

}
