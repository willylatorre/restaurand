package tiramisudelemon.restaurand.app.restaurants;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import tiramisudelemon.restaurand.app.App;
import tiramisudelemon.restaurand.app.R;
import tiramisudelemon.restaurand.app.models.BaseListAdapter;

/**
 * Created by Past on 21/09/2014.
 */

public class RRAdapter extends BaseListAdapter<Restaurant> {

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

        final View view = inflater.inflate(R.layout.list_rr_item, parent, false);
        view.setTag(new RestaurantHolder(view));
        return view;
    }

    @Override
    public void bindView(int position, View view) {

        final Restaurant restaurant = getItem(position);
        bindRRItem(restaurant, (RestaurantHolder) view.getTag());
    }


    void bindRRItem(Restaurant item, RestaurantHolder holder) {

        //Binding party
        holder.rTitle.setText(item.getName());
        holder.rRating.setText(df.format(item.getRating()));
        App.images().loadName(item.getName(), R.drawable.ic_fourchette)
                .placeholder(R.drawable.ic_fourchette)
                .noFade()
                .into(holder.rPicture);
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

}
