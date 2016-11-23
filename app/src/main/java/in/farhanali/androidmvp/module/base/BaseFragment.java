package in.farhanali.androidmvp.module.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author Farhan Ali
 *
 * Provides some basic operations, all fragments should extend this class.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
    }

    /**
     * Binds the viewInteractor objects within the fragment.
     *
     * @param view
     */
    protected void bindViews(View view) {
        ButterKnife.bind(this, view);
    }

    /**
     * Starts an activity with a bundle set to the intent.
     * Didn't invoked 'startActivity' method in BaseActivity to avoid chance of ClassCastException
     * while using with Activity which are not inherited BaseActivity.
     *
     * @param activityClass Class<? extends Activity>
     * @param bundle Bundle
     */
    protected void startActivity(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(), activityClass);

        if (bundle != null) intent.putExtras(bundle);

        startActivity(intent);
    }

}
