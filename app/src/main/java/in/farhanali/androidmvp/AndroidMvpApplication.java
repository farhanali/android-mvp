package in.farhanali.androidmvp;

import android.app.Application;

import in.farhanali.androidmvp.injection.Injector;
import timber.log.Timber;

/**
 * @author Farhan Ali
 */
public class AndroidMvpApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Create application component to make it ready for the injection
        Injector.createApplicationComponent(this);

        // Plant Timber tree for Logging
        Timber.plant(new Timber.DebugTree());
    }

}
