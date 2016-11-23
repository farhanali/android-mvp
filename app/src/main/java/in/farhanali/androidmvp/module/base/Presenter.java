package in.farhanali.androidmvp.module.base;

/**
 * @author Farhan Ali
 */
public interface Presenter<T extends ViewInteractor> {

    void attachViewInteractor(T viewInteractor);

    void detachViewInteractor();

}
