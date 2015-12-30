package tiramisudelemon.restaurand.app.restaurants;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import tiramisudelemon.restaurand.app.App;
import tiramisudelemon.restaurand.app.R;
import tiramisudelemon.restaurand.app.activities.AddRRPlaceActivity;
import tiramisudelemon.restaurand.app.models.BaseListAdapter;

public class RRAdapter extends BaseListAdapter<Restaurand> {

    private final Context mContext;
    private final DecimalFormat df;

    public RRAdapter(Context context) {
        super(context);
        this.mContext = context;

        df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup parent) {

        final View view;
        final Restaurand restaurand = getItem(position);

        switch (restaurand.getType()){
            case DEFAULT:
                view = inflater.inflate(R.layout.list_rr_item, parent, false);
                view.setTag(new RestaurantHolder(view));
                break;
            case EMPTY:
                view = inflater.inflate(R.layout.list_rr_item_empty, parent, false);
                view.setTag(new EmptyHolder(view));
                break;
            default:
                view = inflater.inflate(R.layout.list_rr_item, parent, false);
                view.setTag(new RestaurantHolder(view));
                break;
        }



        return view;
    }

    @Override
    public void bindView(int position, View view) {

        final Restaurand restaurand = getItem(position);

        switch (restaurand.getType()) {
            case DEFAULT:
                bindRRItem(restaurand, (RestaurantHolder) view.getTag());
                break;
            case EMPTY:
                bindEmpty((EmptyHolder) view.getTag());
                break;
        }
    }


    void bindRRItem(Restaurand item, RestaurantHolder holder) {

        //Binding party
        holder.rTitle.setText(item.getName());
        holder.rRating.setText(df.format(item.getRating()));
        App.images().loadResource(R.drawable.ic_fourchette)
                .placeholder(R.drawable.ic_fourchette)
                .noFade()
                .into(holder.rPicture);
    }

    void bindEmpty(EmptyHolder holder) {

        //Binding party
        holder.rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = AddRRPlaceActivity.makeIntent(mContext);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    static class RestaurantHolder {
        @Bind(R.id.itemName)
        TextView rTitle;

        @Bind(R.id.itemRating)
        TextView rRating;

        @Bind(R.id.itemPicture)
        ImageView rPicture;

        RestaurantHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    static class EmptyHolder {

        @Bind(R.id.emptyButton)
        Button rButton;

        EmptyHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
