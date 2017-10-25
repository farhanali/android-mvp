package in.farhanali.androidmvp.data.api;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;


/**
 * Wrapper for rx.Observer with default implementation for onCompleted & onError to work with Retrofit 2.
 *
 * @author Farhan Ali
 */
public abstract class ApiObserver<T extends Response> extends DisposableObserver<T> {

    /**
     * Publish result to observer.
     *
     * @param response
     */
    public abstract void onResponse(T response);

    @Override
    public void onComplete() {
        // Default implementation, can be override accordingly.
    }


    @Override
    public void onNext(T result) {
        onResponse(result);
    }

}

