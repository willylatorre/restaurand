package tiramisudelemon.restaurand.app.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tiramisudelemon.restaurand.app.utils.BuildUtils;

/**
 * Created by work on 29/12/15.
 */
public class PermissionsModuleDexterImpl implements PermissionsModule {

    private Context mContext;

    public PermissionsModuleDexterImpl(Context mContext) {
        this.mContext = mContext;
        Dexter.initialize(mContext);
    }

    @Override
    public boolean hasPermission(String permission) {
        return checkPermission(permission);
    }

    @Override
    public boolean hasPermission(String... permissions) {

        if (mContext == null
                || permissions == null
                || permissions.length == 0) {
            return false;
        }

        for (String permission: permissions) {
            if (!checkPermission(permission)) {
                return false;
            }
        }
        return true;
    }


    private boolean checkPermission(String permission){
        if (!BuildUtils.canIUse(Build.VERSION_CODES.M) || TextUtils.isEmpty(permission)) {
            return true;
        }

        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void checkPermission(final PermissionListener listener, final String permission) {

        Dexter.checkPermission(new com.karumi.dexter.listener.single.PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                listener.onPermissionGranted(response.getPermissionName());
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                listener.onPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                //Do nothing
            }
        }, permission);


    }

    @Override
    public void checkPermissions(final PermissionsListener listener, String... permissions) {


        Dexter.checkPermissions(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

                if(report.areAllPermissionsGranted()){
                    listener.onAllPermissionsGranted();
                    return;
                }

                Map<String, Boolean> permissionsRevoked = new HashMap<String, Boolean>();
                for (PermissionDeniedResponse perm : report.getDeniedPermissionResponses()){
                    permissionsRevoked.put(perm.getPermissionName(), perm.isPermanentlyDenied());
                }

                listener.onPermissionsRevoked(permissionsRevoked);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                //Do nothing
            }
        });


    }
}

