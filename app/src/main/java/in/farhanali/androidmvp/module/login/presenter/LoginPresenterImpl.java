package in.farhanali.androidmvp.module.login.presenter;

import javax.inject.Inject;

import in.farhanali.androidmvp.data.api.ApiObserver;
import in.farhanali.androidmvp.data.api.TodoService;
import in.farhanali.androidmvp.data.model.User;
import in.farhanali.androidmvp.injection.Injector;
import in.farhanali.androidmvp.module.base.BaseNetworkPresenter;
import in.farhanali.androidmvp.module.common.util.UserPreference;
import retrofit2.Response;
import rx.Observable;

/**
 * @author Farhan Ali
 */
public class LoginPresenterImpl extends BaseNetworkPresenter<LoginViewInteractor>
        implements LoginPresenter {

    @Inject TodoService todoService;
    @Inject UserPreference userPreference;

    public LoginPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void verifyLoggedIn() {
        User user = userPreference.read();

        if (user != null) {
            getViewInteractor().onLoggedInVerified(user);
        }
    }

    @Override
    public void doLogin(String email, String password) {
        User user = new User(email, password);
        Observable<Response<User>> loginObservable = todoService.login(user);

        // show progressbar in view just before doing network call
        getViewInteractor().showProgress();

        subscribeForNetwork(loginObservable, new ApiObserver<Response<User>>() {
            @Override
            public void onResponse(Response<User> response) {
                // hid progressbar in view on network call response
                getViewInteractor().hideProgress();

                // if response is either created(201: at first time login with an email)
                // or success(200: during later login with correct credentials
                if (response.code() == 201 || response.code() == 200) {
                    // save user to preference
                    userPreference.save(response.body());
                    // login success, call appropriate view methods
                    getViewInteractor().onLoginSuccess(response.body());
                    return;
                }

                // login failed, call appropriate view methods
                if (response.code() == 401) {
                    getViewInteractor().onLoginFailed("Authentication Failed");
                }
            }

            @Override
            public void onError(Throwable e) {
                // hid progressbar in view on network call error
                getViewInteractor().hideProgress();
                // network call failed, call appropriate view methods
                getViewInteractor().onLoginFailed("Network call failed");
            }
        });
    }

}
