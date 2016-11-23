package in.farhanali.androidmvp.module.common.util;

import javax.inject.Inject;

import in.farhanali.androidmvp.data.model.User;
import in.farhanali.androidmvp.injection.Injector;

/**
 * @author Farhan Ali
 */
public class UserPreference {

    private static final String PREF_USER_INFO = "pref_user_info";

    @Inject PreferenceUtil preferenceUtil;

    public UserPreference() {
        Injector.component().inject(this);
    }

    public void save(User user) {
        preferenceUtil.save(PREF_USER_INFO, user);
    }

    public User read() {
        return (User) preferenceUtil.read(PREF_USER_INFO, User.class);
    }

    public boolean isExist() {
        return read() != null;
    }

    public void clear() {
        preferenceUtil.remove(PREF_USER_INFO);
    }

}
