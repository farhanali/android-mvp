package in.farhanali.androidmvp.module.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import in.farhanali.androidmvp.Config;

/**
 * @author Farhan Ali
 *
 * Provides some basic operations, all activities should extend this class.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private int orientation = Config.ORIENTATION_DEFAULT;

    private Map<Integer, PermissionCallback> permissionCallbackMap = new HashMap<>();

    @Override
    protected void onStart() {
        super.onStart();
        //noinspection WrongConstant
        setRequestedOrientation(orientation);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        bindViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCallback callback = permissionCallbackMap.get(requestCode);

        if (callback == null) return;

        // if permission request cancelled
        if (grantResults.length < 0 && permissions.length > 0) {
            callback.onPermissionDenied(permissions);
            return;
        }

        List<String> grantedPermissions = new ArrayList<>();
        List<String> blockedPermissions = new ArrayList<>();
        List<String> deniedPermissions = new ArrayList<>();
        int index = 0;

        for (String permission : permissions) {
            List<String> permissionList = grantResults[index] == PackageManager.PERMISSION_GRANTED
                    ? grantedPermissions
                    : ! ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                        ? blockedPermissions
                        : deniedPermissions;
            permissionList.add(permission);
            index ++;
        }

        if (grantedPermissions.size() > 0) {
            callback.onPermissionGranted(
                    grantedPermissions.toArray(new String[grantedPermissions.size()]));
        }

        if (deniedPermissions.size() > 0) {
            callback.onPermissionDenied(
                    deniedPermissions.toArray(new String[deniedPermissions.size()]));
        }

        if (blockedPermissions.size() > 0) {
            callback.onPermissionBlocked(
                    blockedPermissions.toArray(new String[blockedPermissions.size()]));
        }

        permissionCallbackMap.remove(requestCode);
    }

    /**
     * Checks weather a permission is granted or not.
     *
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request permission and get result on call back.
     *
     * @param permissions
     * @param callback
     */
    public void requestPermission(String [] permissions, @NonNull PermissionCallback callback) {
        int requestCode = permissionCallbackMap.size() + 1;
        permissionCallbackMap.put(requestCode, callback);
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    /**
     * Request permission and get result on call back.
     *
     * @param permission
     * @param callback
     */
    public void requestPermission(String permission, @NonNull PermissionCallback callback) {
        int requestCode = permissionCallbackMap.size() + 1;
        permissionCallbackMap.put(requestCode, callback);
        ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
    }

    /**
     * Binds the viewInteractor objects within the activity.
     */
    protected void bindViews() {
        ButterKnife.bind(this);
    }

    /**
     * Starts an activity with a bundle set to the intent.
     *
     * @param activityClass Class<? extends Activity>
     * @param bundle Bundle
     */
    protected void startActivity(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);

        if (bundle != null) intent.putExtras(bundle);

        startActivity(intent);
    }

    /**
     * Start an activity by clearing all previous activities.
     *
     * @param activityClass Class<? extends Activity>
     * @param bundle Bundle
     */
    public void startActivityClearTop(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        if (bundle != null) intent.putExtras(bundle);

        startActivity(intent);
    }

    /**
     * Restarts an activity
     */
    protected void restartActivity() {
        finish();
        startActivity(getIntent());
    }

    /**
     * Sets orientation to portrait only.
     */
    protected void setOrientationPortrait() {
        orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    /**
     * Sets orientation to landscape only.
     */
    protected void setOrientationLandscape() {
        orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    /**
     * Sets orientation to sensor mode.
     */
    protected void setOrientationSensor() {
        orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
    }

    /**
     * Set orientation to any supported one.
     *
     * @param orientation ActivityInfo.ORIENTATION_*
     */
    protected void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    /**
     * Get the content view of an activity.
     *
     * @return
     */
    protected View getContentView() {
        return findViewById(android.R.id.content);
    }

}
