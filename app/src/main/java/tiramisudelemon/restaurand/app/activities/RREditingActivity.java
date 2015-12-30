package tiramisudelemon.restaurand.app.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tiramisudelemon.restaurand.app.App;
import tiramisudelemon.restaurand.app.R;
import tiramisudelemon.restaurand.app.dialogs.DeleteWarningDialog;
import tiramisudelemon.restaurand.app.restaurants.Restaurand;

public class RREditingActivity extends AppCompatActivity {

    public static final String EXTRA_RESTAURANT_ID = "restaurandId";
    private static final String DELETE_WARNING_DIALOG_TAG = "DeleteWarningDialog";


    public static Intent makeIntent(Context context, int rrId) {
        Intent intent = new Intent(context, RREditingActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_ID, rrId);
        return intent;
    }

    private Restaurand rest;

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

    @Bind(R.id.editImage)
    ImageView rrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Prevent keyboard to popup
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_editing_detail_layout);
        ButterKnife.bind(this);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.rrplace_detail_title);
        }


        final int rrId = getIntent().getIntExtra(EXTRA_RESTAURANT_ID, 0);
        //retrieve the restaurant
        rest = App.db().searchRestaurantById(rrId);
        fillFields();


    }

    private void fillFields() {
        if (rest != null) {
//            App.images().loadImageIntoView(rest.getName(), rrImage);
            rrName.setText(rest.getName());
            rrAddress.setText(rest.getAddress());
            rrPhone.setText(rest.getPhone());
            rrWebsite.setText(rest.getWebsite());
            rrRating.setRating((float) rest.getRating());
        }
    }

    @OnClick(R.id.rrSave)
    public void saveRR() {

        rest.setName(rrName.getText().toString())
                .setAddress(rrAddress.getText().toString())
                .setPhone(rrPhone.getText().toString())
                .setWebsite(rrWebsite.getText().toString())
                .setRating(rrRating.getRating());

        App.db().updateRestaurant(rest);
        goToMain();
    }

    private void goToMain() {

        final Intent intent = HomeActivity.makeIntent(this, HomeActivity.FRAGMENT_HOME);
        startActivity(intent);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_delete:
                showWarning();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showWarning() {

        final DeleteWarningDialog dialog = DeleteWarningDialog.newInstance();
        dialog.setPositiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRR();
                    }
                })
                .show(getSupportFragmentManager(),DELETE_WARNING_DIALOG_TAG );
    }

    private void deleteRR() {
        App.db().deleteRR(rest);
        goToMain();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_remove, menu);
        return true;
    }

}
