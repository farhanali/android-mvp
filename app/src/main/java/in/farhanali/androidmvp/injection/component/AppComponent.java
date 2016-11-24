package in.farhanali.androidmvp.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import in.farhanali.androidmvp.injection.module.ApiModule;
import in.farhanali.androidmvp.injection.module.AppModule;
import in.farhanali.androidmvp.injection.module.CommonModule;
import in.farhanali.androidmvp.injection.module.LoginModule;
import in.farhanali.androidmvp.injection.module.TaskModule;
import in.farhanali.androidmvp.module.common.util.Bakery;
import in.farhanali.androidmvp.module.common.util.ConnectivityUtil;
import in.farhanali.androidmvp.module.common.util.PreferenceUtil;
import in.farhanali.androidmvp.module.common.util.UserPreference;
import in.farhanali.androidmvp.module.login.presenter.LoginPresenterImpl;
import in.farhanali.androidmvp.module.login.view.LoginActivity;
import in.farhanali.androidmvp.module.task.presenter.TaskListPresenterImpl;
import in.farhanali.androidmvp.module.task.view.TaskListActivity;

/**
 * @author Farhan Ali
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        CommonModule.class,
        LoginModule.class,
        TaskModule.class,
})
public interface AppComponent {

    // common module
    void inject(PreferenceUtil preferenceUtil);
    void inject(Bakery bakery);
    void inject(ConnectivityUtil connectivityUtil);
    void inject(UserPreference userPreference);

    // login module
    void inject(LoginActivity loginActivity);
    void inject(LoginPresenterImpl loginPresenter);

    // task module
    void inject(TaskListActivity taskListActivity);
    void inject(TaskListPresenterImpl taskListPresenter);

}
