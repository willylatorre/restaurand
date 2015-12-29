package tiramisudelemon.restaurand.app;

import android.app.Application;
import android.content.Context;

import tiramisudelemon.restaurand.app.media.ImagesModule;
import tiramisudelemon.restaurand.app.models.DatabaseHelper;
import tiramisudelemon.restaurand.app.models.DatabaseModule;
import tiramisudelemon.restaurand.app.permissions.PermissionsModule;
import tiramisudelemon.restaurand.app.permissions.PermissionsModuleDexterImpl;

/**
 * Created by work on 17/12/15.
 */
public class App extends Application {

    private static App app;

    private DatabaseModule dbModule;
    private PermissionsModule permissionsModule;
    private ImagesModule imagesModule;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        dbModule = initDBModule();
        initPermissionsModule();
        imagesModule = initImages();
    }

    private DatabaseModule initDBModule() {

        return new DatabaseHelper(getApplicationContext());

    }

    private void initPermissionsModule() {
        permissionsModule = new PermissionsModuleDexterImpl(getApplicationContext());
    }

    private ImagesModule initImages() {
        return new ImagesModule(getApplicationContext());
    }

    public static App getApp() {
        return app;
    }

    public static Context getContext() {
        return app.getApplicationContext();
    }

    public static DatabaseModule db() {
        return app.dbModule;
    }

    public static PermissionsModule permissions() {
        return app.permissionsModule;
    }

    public static ImagesModule images() {
        return app.imagesModule;
    }

}
