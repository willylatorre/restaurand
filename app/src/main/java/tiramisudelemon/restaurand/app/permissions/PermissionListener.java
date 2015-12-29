package tiramisudelemon.restaurand.app.permissions;

/**
 * Created by work on 29/12/15.
 */
public interface PermissionListener {

        void onPermissionGranted(String permission);

        void onPermissionDenied(String permission, boolean permanentlyDenied);

//        void onPermissionRationaleShouldBeShown(String permission, String token);
}
