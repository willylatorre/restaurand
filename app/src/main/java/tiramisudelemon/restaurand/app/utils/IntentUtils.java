package tiramisudelemon.restaurand.app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by work on 18/12/15.
 */
public class IntentUtils {


    public static boolean canBeLaunched(Context context, Intent intent) {
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    public static Intent getMapsIntent(final String address) {
        StringBuilder uri = new StringBuilder();
        uri.append("geo:0,0?q=")
                .append(address);
        return new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri.toString()));
    }

    public static Intent getCallIntent(final String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        return intent;
    }

    public static Intent getWebIntent(final String url) {
        final Uri webpage = Uri.parse(url);
        return new Intent(Intent.ACTION_VIEW, webpage);
    }


}
