package tiramisudelemon.restaurand.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tiramisudelemon.restaurand.app.App;
import tiramisudelemon.restaurand.app.R;
import tiramisudelemon.restaurand.app.activities.RREditingActivity;
import tiramisudelemon.restaurand.app.restaurants.RRAdapter;
import tiramisudelemon.restaurand.app.restaurants.RRType;
import tiramisudelemon.restaurand.app.restaurants.Restaurand;
import tiramisudelemon.restaurand.app.views.LinearListView;

public class RRListFragment extends Fragment {

    public static RRListFragment newInstance() {
        final Bundle bundle = new Bundle();

        final RRListFragment fragment = new RRListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    private RRAdapter adapter;
    private List<Restaurand> listRestaurands;

    @Bind(R.id.rr_list)
    LinearListView rrList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new RRAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rr_list, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listRestaurands = App.db().getAllRestaurants();
        rrList.setAdapter(adapter);
        rrList.setOnItemClickListener(new LinearListView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView view, View clicked, BaseAdapter adapter, int position) {
                Restaurand item = listRestaurands.get(position);

                if (item != null) {
                    final Intent intent = RREditingActivity.makeIntent(getActivity(), item.getId());
                    startActivity(intent);
                }
            }
        });

        if(listRestaurands.isEmpty()){
            listRestaurands.add(new Restaurand().setType(RRType.EMPTY));
        }

        adapter.setItems(listRestaurands);
    }

}
