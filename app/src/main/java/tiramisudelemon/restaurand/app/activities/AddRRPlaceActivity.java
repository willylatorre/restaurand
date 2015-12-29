package tiramisudelemon.restaurand.app.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tiramisudelemon.restaurand.app.App;
import tiramisudelemon.restaurand.app.R;
import tiramisudelemon.restaurand.app.media.ImagesModule;
import tiramisudelemon.restaurand.app.permissions.PermissionListener;
import tiramisudelemon.restaurand.app.restaurants.Restaurant;
import tiramisudelemon.restaurand.app.utils.ToastUtils;

public class AddRRPlaceActivity extends AppCompatActivity {

    final int PLACE_PICKER_REQUEST = 1001;

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddRRPlaceActivity.class);
    }

    private GoogleApiClient mGoogleApiClient;
    private Bitmap googlePlacePicture;


    @Bind(R.id.rrName)
    EditText rrName;

    @Bind(R.id.rrAddress)
    EditText rrAddress;

    @Bind(R.id.rrPhone)
    EditText rrPhone;

    @Bind(R.id.rrWebsite)
    EditText rrWebsite;

    @Bind(R.id.rrRating)
    RatingBar rrRating;

    private Place googlePlace;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rrplace);
        ButterKnife.bind(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.add_rrplace_activity_title);
        }

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(mConnectionCallbacks)
                .addOnConnectionFailedListener(mOnConnectionFailedListener)
                .build();

    }


    private void fillPlace(Place place) {

        rrName.setText(place.getName());
        rrAddress.setText(place.getAddress());
        rrPhone.setText(place.getPhoneNumber());
        rrWebsite.setText(place.getWebsiteUri() != null ? place.getWebsiteUri().toString() : "");
        rrRating.setRating(place.getRating() * 10);
    }


    private Restaurant getRRfromForm() {

        final Restaurant rest = new Restaurant()
                .setName(rrName.getText().toString())
                .setAddress(rrAddress.getText().toString())
                .setPhone(rrPhone.getText().toString())
                .setWebsite(rrWebsite.getText().toString())
                .setRating(rrRating.getRating());

        return rest;
    }


    @OnClick(R.id.rrSave)
    public void saveRR() {

        //Save picture
        if (googlePlacePicture != null) {
            final ImagesModule images = App.images();
            if (!images.fileExists(googlePlace.getName().toString())) {
                images.saveImage(googlePlacePicture, googlePlace.getName().toString());
            }
        }
        //Save restaurand
        App.db().createRest(getRRfromForm());
        ToastUtils.show(R.string.rr_added);
        goToMain();
    }

    private void goToMain() {

        final Intent intent = HomeActivity.makeIntent(this, HomeActivity.FRAGMENT_HOME);
        startActivity(intent);

    }


    @OnClick(R.id.add_rrplace_pickup_place_btn)
    public void makeGooglePlacesIntent() {

        App.permissions().checkPermission(new PermissionListener() {
            @Override
            public void onPermissionGranted(String permission) {
                launchPlacesIntent();
            }

            @Override
            public void onPermissionDenied(String permission, boolean permanentlyDenied) {
                launchPlacesIntent();
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION);

    }

    private void launchPlacesIntent() {

        googlePlacePicture = null;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                googlePlace = PlacePicker.getPlace(this, data);

                getPlaceImage(googlePlace.getId());
                fillPlace(googlePlace);
            }
        }
    }

    private void getPlaceImage(String placeId) {

        Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {


                    @Override
                    public void onResult(PlacePhotoMetadataResult photos) {
                        if (!photos.getStatus().isSuccess()) {
                            return;
                        }

                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                        if (photoMetadataBuffer.getCount() > 0) {
                            // Display the first bitmap in an ImageView in the size of the view
                            photoMetadataBuffer.get(0)
                                    .getPhoto(mGoogleApiClient)
                                    .setResultCallback(mDisplayPhotoResultCallback);
                        }
                        photoMetadataBuffer.release();
                    }
                });


    }

    private ResultCallback<PlacePhotoResult> mDisplayPhotoResultCallback
            = new ResultCallback<PlacePhotoResult>() {
        @Override
        public void onResult(PlacePhotoResult placePhotoResult) {
            if (!placePhotoResult.getStatus().isSuccess()) {
                return;
            }
            googlePlacePicture = placePhotoResult.getBitmap();

        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private final GoogleApiClient.ConnectionCallbacks mConnectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {
            //On connected

        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };

    @SuppressWarnings("FieldCanBeLocal")
    private final GoogleApiClient.OnConnectionFailedListener mOnConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            //On connection failedd
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
