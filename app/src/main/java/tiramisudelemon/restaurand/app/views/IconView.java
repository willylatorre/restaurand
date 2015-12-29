package tiramisudelemon.restaurand.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.ImageView;

import tiramisudelemon.restaurand.app.R;

public class IconView extends ImageView {

    public IconView(Context context) {
        this(context, null);
    }

    public IconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {

        final TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.IconView);
        try {

            final int color = typedArray.getColor(R.styleable.IconView_iv_imageColor, 0);
            if (color != 0) {
                setImageColor(color);
            }
        } finally {
            typedArray.recycle();
        }
    }

    public void setImageColor(@ColorInt int color) {
        setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
    }
}
