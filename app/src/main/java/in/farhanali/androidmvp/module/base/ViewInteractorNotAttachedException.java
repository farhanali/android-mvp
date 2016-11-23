package in.farhanali.androidmvp.module.base;

/**
 * @author Farhan Ali
 */
public class ViewInteractorNotAttachedException extends RuntimeException {

    public ViewInteractorNotAttachedException() {
        super("Please call Presenter.attachViewViewInteractor(viewInteractor) before" +
                " requesting data to the Presenter");
    }

}