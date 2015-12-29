package tiramisudelemon.restaurand.app.utils;

import android.os.Build;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;
import static android.os.Build.VERSION_CODES.M;

public class BuildUtils {

    private BuildUtils() {}

    @IntDef({
            ICE_CREAM_SANDWICH,
            ICE_CREAM_SANDWICH_MR1,
            JELLY_BEAN,
            JELLY_BEAN_MR1,
            JELLY_BEAN_MR2,
            KITKAT,
            LOLLIPOP,
            LOLLIPOP_MR1,
            M
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ApiVersionCode {}

    public static boolean canIUse(@ApiVersionCode int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }
}
