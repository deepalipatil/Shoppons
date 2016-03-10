package com.shopons.view.fragment;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

/**
 * Created by deepali on 5/3/16.
 */
public abstract class PermissionFragment extends BaseFragment {

    protected static final int PERMISSIONS_REQUEST_ACCESS = 1;

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(permissions.length == 0){
            return;
        }
        if (requestCode == PERMISSIONS_REQUEST_ACCESS
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted(permissions[0]);
            return;
        }
        onPermissionDenied(permissions[0]);
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    protected void checkForPermission(final String permission) {
        if (TextUtils.isEmpty(permission))
            throw new RuntimeException("Permission must be present!");
        if (ActivityCompat.checkSelfPermission(getActivity(), permission)
                == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{permission},
                    PERMISSIONS_REQUEST_ACCESS);
        } else {
            // if permission is already granted then directly check for the GPS and access location
            alreadyGrantedPermission(permission);
        }
    }

    public abstract void onPermissionGranted(final String permission);

    public abstract void onPermissionDenied(final String permission);

    public abstract void alreadyGrantedPermission(final String permission);

}

