package tiramisudelemon.restaurand.app.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    private final LayoutInflater mInflater;
    private List<T> mItems;

    public BaseListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setItems(List<T> items) {
        setItems(items, true);
    }

    public void setItems(List<T> items, boolean notifyDataSetChanged) {
        mItems = items;
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        if (convertView == null) {
            view = newView(mInflater, position, parent);
        } else {
            view = convertView;
        }

        bindView(position, view);

        return view;
    }

    public abstract View newView(LayoutInflater inflater, int position, ViewGroup parent);
    public abstract void bindView(int position, View view);

    protected LayoutInflater getInflater() {
        return mInflater;
    }
}
