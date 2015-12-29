package tiramisudelemon.restaurand.app.views;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class LinearListView extends LinearLayout {

    public interface OnItemClickListener {
        void onItemClick(LinearListView view, View clicked, BaseAdapter adapter, int position);
    }

    private final List<View> mViews = new ArrayList<>();
    private BaseAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;

    public LinearListView(Context context) {
        this(context, null);
    }

    public LinearListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        setOrientation(VERTICAL);
    }

    private final DataSetObserver mAdapterObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            bindViews();
        }

        @Override
        public void onInvalidated() {
            removeAllViews();
            mViews.clear();
            bindViews();
        }
    };

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setSelectedItemPosition(int position) {
        final int size = mViews.size();
        if (position >= size) {
            return;
        }
        View view;
        for (int i = 0; i < size; i++) {
            view = mViews.get(i);
            view.setActivated(i == position);
        }
    }

    public void notifyItemChanged(int position) {
        if (mAdapter == null
                || position >= mViews.size()) {
            return;
        }
        bindView(position);
    }

    public void setAdapter(BaseAdapter adapter) {

        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mAdapterObserver);
        }

        if (getChildCount() != 0) {
            removeAllViews();
            mViews.clear();
        }

        mAdapter = adapter;
        bindViews();
        mAdapter.registerDataSetObserver(mAdapterObserver);
    }

    private void bindViews() {
        final int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            bindView(i);
        }
    }

    private void bindView(int i) {
        final View view = mAdapter.getView(i, getConvertView(i), this);
        final boolean isEnabled = mAdapter.isEnabled(i);
        if (mOnItemClickListener != null
                && isEnabled) {
            view.setOnClickListener(new ItemClickListener(i));
        } else {
            view.setOnClickListener(null);
        }
        view.setEnabled(isEnabled);

        if (view.getParent() == null) {
            mViews.add(i, view);
            addView(view, i);
        }
    }

    private View getConvertView(int position) {
        if (position >= mViews.size()) {
            return null;
        }
        return mViews.get(position);
    }

    private class ItemClickListener implements OnClickListener {

        private final int position;

        private ItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(LinearListView.this, v, mAdapter, position);
            }
        }
    }
}
