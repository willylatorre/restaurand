package tiramisudelemon.restaurand.app.restaurants;

import android.support.annotation.DrawableRes;

/**
 * Created by work on 28/12/15.
 */
public class RRDetailItem {

    public final static int TYPE_ADDRESS = 0;
    public final static int TYPE_PHONE = 1;
    public final static int TYPE_WEBSITE = 2;

    private @DrawableRes int iconRes;
    private String content;
    private String actionURI;
    private int type;


    public int getType() {
        return type;
    }

    public RRDetailItem setType(int type) {
        this.type = type;
        return this;
    }

    public int getIconRes() {
        return iconRes;
    }

    public RRDetailItem setIconRes(int iconRes) {
        this.iconRes = iconRes;
        return this;
    }

    public String getContent() {
        return content;
    }

    public RRDetailItem setContent(String content) {
        this.content = content;
        return this;
    }

    public String getActionURI() {
        return actionURI;
    }

    public RRDetailItem setActionURI(String actionURI) {
        this.actionURI = actionURI;
        return this;
    }
}
