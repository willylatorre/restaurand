package tiramisudelemon.restaurand.app.media;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import tiramisudelemon.restaurand.app.R;

public class ImagesModule {

    private static final String PHOTO_NAME_PATTERN = "%1$s_%2$s.jpg";

    private Context mContext;

    public ImagesModule(Context mContext) {
        this.mContext = mContext;
    }

    public RequestCreator downloadImage(String url) {
        return Picasso.with(mContext)
                .load(url);
    }

    public RequestCreator loadFile(File file) {
        return Picasso.with(mContext)
                .load(file);
    }

    public RequestCreator loadResource(@DrawableRes int drawRes) {
        return Picasso.with(mContext)
                .load(drawRes);
    }

    public RequestCreator loadName(String name, @DrawableRes int placeholder) {

        RequestCreator picassoCreator;
        if (fileExists(name)) {
            picassoCreator = loadFile(getImageFilePath(name));
        } else {
            picassoCreator = loadResource(placeholder);
        }

        return picassoCreator;
    }


    public void loadImageIntoView(String name, ImageView view) {

        final int placeholder = R.drawable.rest_card;
        final RequestCreator picassoCreator = loadName(name, placeholder);
        picassoCreator.placeholder(placeholder)
                .into(view);
    }


    private String createPhotoFileName(String name) {
        return String.format(
                PHOTO_NAME_PATTERN,
                "rr",
                name
        );
    }

    //Closes the file
    private void closeSilently(@Nullable Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }


    public File getImageFilePath(String name) {

        ContextWrapper cw = new ContextWrapper(mContext);

        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("rr_images", Context.MODE_PRIVATE);

        // Create imageDir
        return new File(directory, createPhotoFileName(name));

    }


    public String saveImage(Bitmap bitmapImage, String name) {

        File mypath = getImageFilePath(name);

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            closeSilently(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mypath.getAbsolutePath();
    }

    public Bitmap loadImage(String name) {
        Bitmap bitmap = null;

        if(!fileExists(name)){
            return BitmapFactory.decodeResource(mContext.getResources(),R.drawable.rest_card);
        }

        try {

            File f = getImageFilePath(name);
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
            options.inScaled = false;
            bitmap = BitmapFactory.decodeStream(fis, null, options);
            bitmap.getHeight();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    public boolean fileExists(String name) {
        File f = getImageFilePath(name);
        return f.exists();
    }


}
