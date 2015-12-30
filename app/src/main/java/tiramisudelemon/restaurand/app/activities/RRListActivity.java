package tiramisudelemon.restaurand.app.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tiramisudelemon.restaurand.app.App;
import tiramisudelemon.restaurand.app.R;
import tiramisudelemon.restaurand.app.dialogs.NoMoreRRDialog;
import tiramisudelemon.restaurand.app.restaurants.Restaurand;


public class RRListActivity extends AppCompatActivity {

    private static final String NO_MORE_RR_DIALOG_TAG = "NoMoreRRDialog";

    public static Intent makeIntent(Context context) {
        return new Intent(context, RRListActivity.class);
    }


    //RRvalues
    @Bind(R.id.randTitle)
    TextView randTitle;
    @Bind(R.id.randImg)
    ImageView randImg;
    @Bind(R.id.randAgain)
    Button randAgain;
    @Bind(R.id.randOK)
    Button randOk;

    private Restaurand selectedRR;
    private List<Restaurand> rrList;
    private int rrId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_layout);
        ButterKnife.bind(this);

        rrList = App.db().getAllRestaurants();
        Collections.shuffle(rrList);
        //TODO: Implement better shuffling

        rrId = 0;
        selectedRR = rrList.get(rrId);

        setRR(selectedRR);
    }

    private void setRR(Restaurand restaurand) {
        App.images().loadImageIntoView(restaurand.getName(), randImg);
        randTitle.setText(restaurand.getName());
    }

    @OnClick(R.id.randOK)
    public void restaurandSelected() {
        App.db().updateRestaurantRndFactor(selectedRR);

        final Intent intent = RRDetailActivity.makeIntent(RRListActivity.this, selectedRR.getId());
        startActivity(intent);
    }

    @OnClick(R.id.randAgain)
    public void nextRR() {
        rrId++;
        if (rrId < rrList.size()) {
            selectedRR = rrList.get(rrId);
            App.db().increaseRestaurantRndFactor(selectedRR);
            setRR(selectedRR);
        } else {
            showAlertDialog();
        }

    }

    private void showAlertDialog() {

        final NoMoreRRDialog noMoreRRDialog = NoMoreRRDialog.newInstance();
        noMoreRRDialog.setPositiveListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Intent intent = AddRRPlaceActivity.makeIntent(RRListActivity.this);
                startActivity(intent);
            }
        })
                .show(getSupportFragmentManager(), NO_MORE_RR_DIALOG_TAG);

    }
    // Implementing Fisher–Yates shuffle
//    static void shuffleArray(Restaurand[] ar)
//    {
//        Random rnd = new Random();
//        for (int i = ar.length - 1; i > 0; i--)
//        {
//            int index = rnd.nextInt(i + 1);
//            // Simple swap
//            Restaurand a = ar[index];
//            ar[index] = ar[i];
//            ar[i] = a;
//        }
//    }


}
