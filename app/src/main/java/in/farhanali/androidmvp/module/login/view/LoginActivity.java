package in.farhanali.androidmvp.module.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import in.farhanali.androidmvp.R;
import in.farhanali.androidmvp.data.model.User;
import in.farhanali.androidmvp.injection.Injector;
import in.farhanali.androidmvp.module.base.BaseActivity;
import in.farhanali.androidmvp.module.common.util.Bakery;
import in.farhanali.androidmvp.module.login.presenter.LoginPresenter;
import in.farhanali.androidmvp.module.login.presenter.LoginViewInteractor;
import in.farhanali.androidmvp.module.task.view.TaskListActivity;

/**
 * @author Farhan Ali
 */

public class LoginActivity extends BaseActivity implements LoginViewInteractor {

    @Inject LoginPresenter loginPresenter;
    @Inject Bakery bakery;

    @Bind(R.id.progress) ProgressBar progress;
    @Bind(R.id.edt_email) EditText edtEmail;
    @Bind(R.id.edt_password) EditText edtPassword;

    // activity lifecycle methods

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // the implementation in BaseActivity will do the binding using butterknife
        setContentView(R.layout.login_activity);

        // to inject dependencies by dagger 2
        Injector.component().inject(this);

        // attach view interactor for the presenter
        loginPresenter.attachViewInteractor(this);

        // verify already logged in
        loginPresenter.verifyLoggedIn();
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        loginPresenter.doLogin(email, password);
    }

    // view interactor method implementations

    @Override
    public void onLoggedInVerified(User user) {
        bakery.toastShort(user.getEmail());
        loadTaskActivity();
    }

    @Override
    public void onLoginSuccess(User user) {
        bakery.toastShort("Success: " + user.getEmail());
        loadTaskActivity();
    }

    @Override
    public void onLoginFailed(String message) {
        bakery.toastShort(message);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    // activity private methods

    private void loadTaskActivity() {
        startActivity(TaskListActivity.class, null);
        finish();
    }

}
