package tiramisudelemon.restaurand.app.permissions;

import android.content.Context;

/**
 * Created by work on 29/12/15.
 */
public interface PermissionsModule {

    boolean hasPermission(String permission);
    boolean hasPermission(String... permissions);

    void checkPermission(PermissionListener listener, String permission );
    void checkPermissions(PermissionsListener listener, String... permissions );

}
