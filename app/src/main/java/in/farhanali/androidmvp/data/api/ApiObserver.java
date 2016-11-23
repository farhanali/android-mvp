package in.farhanali.androidmvp.data.api;

import retrofit2.Response;
import rx.Observer;

/**
 * Wrapper for rx.Observer with default implementation for onCompleted & onError to work with Retrofit 2.
 *
 * @author Farhan Ali
 */
public abstract class ApiObserver<T extends Response> implements Observer<T> {

    /**
     * Publish result to observer.
     *
     * @param response
     */
    public abstract void onResponse(T response);

    @Override
    public void onCompleted() {
        // Default implementation, can be override accordingly.
    }

    @Override
    public void onNext(T result) {
        onResponse(result);
    }

}

