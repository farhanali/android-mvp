package in.farhanali.androidmvp.injection.module;

import dagger.Module;
import dagger.Provides;
import in.farhanali.androidmvp.module.login.presenter.LoginPresenter;
import in.farhanali.androidmvp.module.login.presenter.LoginPresenterImpl;

/**
 * Provides all login module dependencies.
 *
 * @author Farhan Ali
 */
@Module
public class LoginModule {

    @Provides
    public LoginPresenter provideLoginPresenter() {
        return new LoginPresenterImpl();
    }

}
