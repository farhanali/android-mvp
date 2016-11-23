package in.farhanali.androidmvp.module.login.presenter;

import in.farhanali.androidmvp.module.base.Presenter;

/**
 * @author Farhan Ali
 */
public interface LoginPresenter extends Presenter<LoginViewInteractor> {

    void doLogin(String email, String password);

}
