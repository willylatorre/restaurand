package tiramisudelemon.restaurand.app.permissions;

import java.util.Map;

/**
 * Created by work on 29/12/15.
 */
public interface PermissionsListener {

    void onAllPermissionsGranted();

    void onPermissionsRevoked(Map<String, Boolean> permissionsDenied);
}
