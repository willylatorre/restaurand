package tiramisudelemon.restaurand.app.restaurants;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import tiramisudelemon.restaurand.app.R;
import tiramisudelemon.restaurand.app.models.BaseListAdapter;
import tiramisudelemon.restaurand.app.views.IconView;

/**
 * Created by Past on 21/09/2014.
 */

public class RRDetailsAdapter extends BaseListAdapter<RRDetailItem> {

    private final Context mContext;

    public RRDetailsAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup parent) {

        final View view = inflater.inflate(R.layout.list_rr_detail_item, parent, false);
        view.setTag(new RRDetailItemHolder(view));
        return view;
    }

    @Override
    public void bindView(int position, View view) {

        final RRDetailItem restaurant = getItem(position);
        bindRRItem(restaurant, (RRDetailItemHolder) view.getTag());
    }


    void bindRRItem(RRDetailItem item, RRDetailItemHolder holder) {

        //Binding party
        holder.rContent.setText(item.getContent());
        holder.rIcon.setImageResource(item.getIconRes());
        holder.rAction.setImageResource(R.drawable.ic_forward_white_24dp);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    static class RRDetailItemHolder {
        @Bind(R.id.detail_item_icon)
        IconView rIcon;

        @Bind(R.id.detail_item_content)
        TextView rContent;

        @Bind(R.id.detail_item_action)
        IconView rAction;

        RRDetailItemHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
