package in.farhanali.androidmvp.module.login.presenter;

import javax.inject.Inject;

import in.farhanali.androidmvp.data.api.ApiObserver;
import in.farhanali.androidmvp.data.api.TodoService;
import in.farhanali.androidmvp.data.model.User;
import in.farhanali.androidmvp.injection.Injector;
import in.farhanali.androidmvp.module.base.BaseNetworkPresenter;
import retrofit2.Response;
import rx.Observable;

/**
 * @author Farhan Ali
 */
public class LoginPresenterImpl extends BaseNetworkPresenter<LoginViewInteractor>
        implements LoginPresenter {

    @Inject TodoService todoService;

    public LoginPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void doLogin(String email, String password) {
        User user = new User(email, password);
        Observable<Response<User>> loginObservable = todoService.login(user);

        subscribeForNetwork(loginObservable, new ApiObserver<Response<User>>() {
            @Override
            public void onResponse(Response<User> response) {
                if (response.code() == 200) {
                    getViewInteractor().onLoginSuccess(response.body());
                    return;
                }

                if (response.code() == 401) {
                    getViewInteractor().onLoginFailed("Authentication Failed");
                }
            }

            @Override
            public void onError(Throwable e) {
                // network call failed due to some error, do something
                getViewInteractor().onLoginFailed("Network call failed");
            }
        });
    }

}
