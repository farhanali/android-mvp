package in.farhanali.androidmvp.module.login.presenter;

import in.farhanali.androidmvp.data.model.User;
import in.farhanali.androidmvp.module.base.ViewInteractor;

/**
 * @author Farhan Ali
 */
public interface LoginViewInteractor extends ViewInteractor {

    void showProgress();

    void hidProgress();

    void onLoginSuccess(User user);

    void onLoginFailed(String message);

}
