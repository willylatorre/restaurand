package tiramisudelemon.restaurand.app.utils;

import android.content.res.Resources;
import android.widget.Toast;

import tiramisudelemon.restaurand.app.App;

/**
 * Created by work on 18/12/15.
 */
public class ToastUtils {

    public static void show(String str) {
        Toast.makeText(App.getApp(), str, Toast.LENGTH_SHORT).show();
    }

    public static void show(int strResId) {
        try {
            Toast.makeText(App.getApp(), strResId, Toast.LENGTH_SHORT).show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}
