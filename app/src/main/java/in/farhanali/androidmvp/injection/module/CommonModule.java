package in.farhanali.androidmvp.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import in.farhanali.androidmvp.module.common.util.Bakery;
import in.farhanali.androidmvp.module.common.util.ConnectivityUtil;
import in.farhanali.androidmvp.module.common.util.PreferenceUtil;
import in.farhanali.androidmvp.module.common.util.UserPreference;

/**
 * Provides all common module dependencies.
 *
 * @author Farhan Ali
 */
@Module
public class CommonModule {

    @Provides
    @Singleton
    public PreferenceUtil providePreferenceUtil() {
        return new PreferenceUtil();
    }

    @Provides
    @Singleton
    public Bakery provideBakery() {
        return new Bakery();
    }

    @Provides
    @Singleton
    public UserPreference provideUserInfoPreference() {
        return new UserPreference();
    }

    @Provides
    @Singleton
    public ConnectivityUtil provideConnectivityUtil() {
        return new ConnectivityUtil();
    }

}
