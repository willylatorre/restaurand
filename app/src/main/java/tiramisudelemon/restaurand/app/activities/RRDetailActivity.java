package tiramisudelemon.restaurand.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tiramisudelemon.restaurand.app.App;
import tiramisudelemon.restaurand.app.R;
import tiramisudelemon.restaurand.app.restaurants.RRDetailItem;
import tiramisudelemon.restaurand.app.restaurants.RRDetailsAdapter;
import tiramisudelemon.restaurand.app.restaurants.Restaurand;
import tiramisudelemon.restaurand.app.utils.IntentUtils;
import tiramisudelemon.restaurand.app.utils.ToastUtils;
import tiramisudelemon.restaurand.app.views.LinearListView;

public class RRDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RESTAURANT_ID = "restaurandId";


    public static Intent makeIntent(Context context, int rrId) {
        Intent intent = new Intent(context, RRDetailActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_ID,rrId);
        return intent;
    }

    private Context mContext;
    private Restaurand rest;
    private ArrayList<RRDetailItem> mRestDetails;
    private RRDetailsAdapter mAdapter;


    @Bind(R.id.detail_list)
    LinearListView detailList;
    @Bind(R.id.detailTitle)
    TextView detailTitle;
    @Bind(R.id.detailImg)
    ImageView detailImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.results_detail_layout);
        ButterKnife.bind(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.rrplace_detail_title);
        }

        mAdapter = new RRDetailsAdapter(this);

        final int rrId = getIntent().getIntExtra(EXTRA_RESTAURANT_ID, 0);
        rest = App.db().searchRestaurantById(rrId);

        detailList.setAdapter(mAdapter);
        detailList.setOnItemClickListener(new LinearListView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView view, View clicked, BaseAdapter adapter, int position) {

                final RRDetailItem detailItem = mRestDetails.get(position);
                final String actionUri = detailItem.getActionURI();

                switch (detailItem.getType()){
                    case RRDetailItem.TYPE_ADDRESS:
                        launchAddress(actionUri);
                        break;
                    case RRDetailItem.TYPE_PHONE:
                        launchPhone(actionUri);
                        break;
                    case RRDetailItem.TYPE_WEBSITE:
                        launchWebsite(actionUri);
                        break;
                }
            }
        });

        mRestDetails = new ArrayList<>();

        if (rest != null) {
            detailTitle.setText(rest.getName());
//            App.images().loadImageIntoView(rest.getName(),detailImg);
            fillDetailsFromRest();
            mAdapter.setItems(mRestDetails);
        }
    }

    private void launchWebsite(String website) {
        if (TextUtils.isEmpty(website)) {
            Toast.makeText(RRDetailActivity.this, R.string.alert_no_website, Toast.LENGTH_SHORT).show();
        } else {
            final Intent intent = IntentUtils.getWebIntent(website);
            startActivity(intent);
        }
    }

    private void launchPhone(String phone) {
        final Intent intent = IntentUtils.getCallIntent(phone);
        if (IntentUtils.canBeLaunched(mContext, intent)) {
            startActivity(intent);
        } else {
            ToastUtils.show(R.string.alert_no_phone);
        }

    }

    private void launchAddress(String address) {
        final Intent intent = IntentUtils.getMapsIntent(address);
        if (IntentUtils.canBeLaunched(mContext, intent)) {
            startActivity(intent);
        } else {
            ToastUtils.show(R.string.alert_no_maps);
        }
    }


    private void fillDetailsFromRest() {

        mRestDetails.clear();

        if(!TextUtils.isEmpty(rest.getAddress())){
            //Address
            final RRDetailItem addressItem = new RRDetailItem()
                    .setContent(rest.getAddress())
                    .setActionURI(rest.getAddress())
                    .setIconRes(R.drawable.ic_directions_walk_white_24dp)
                    .setType(RRDetailItem.TYPE_ADDRESS);
            mRestDetails.add(addressItem);
        }

        if(!TextUtils.isEmpty(rest.getPhone())) {
            //PHone
            final RRDetailItem phoneItem = new RRDetailItem()
                    .setContent(rest.getPhone())
                    .setActionURI(rest.getPhone())
                    .setIconRes(R.drawable.ic_settings_phone_white_24dp)
                    .setType(RRDetailItem.TYPE_PHONE);
            mRestDetails.add(phoneItem);

        }

        if(!TextUtils.isEmpty(rest.getWebsite())) {
            //Website
            final RRDetailItem webItem = new RRDetailItem()
                    .setContent(rest.getWebsite())
                    .setActionURI(rest.getWebsite())
                    .setIconRes(R.drawable.ic_link_white_24dp)
                    .setType(RRDetailItem.TYPE_WEBSITE);
            mRestDetails.add(webItem);
        }


    }

    @OnClick(R.id.rrHome)
    public void goToHome(){
        final Intent intent = HomeActivity.makeIntent(RRDetailActivity.this, HomeActivity.FRAGMENT_HOME);
        startActivity(intent);
    }

}
