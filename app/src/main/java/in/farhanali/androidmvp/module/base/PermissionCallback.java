package in.farhanali.androidmvp.module.base;

/**
 * @author Farhan Ali
 */
public interface PermissionCallback {

    void onPermissionGranted(String[] grantedPermissions);

    void onPermissionDenied(String[] deniedPermissions);

    void onPermissionBlocked(String[] blockedPermissions);

}
