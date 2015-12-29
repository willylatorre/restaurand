package tiramisudelemon.restaurand.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import tiramisudelemon.restaurand.app.App;
import tiramisudelemon.restaurand.app.R;
import tiramisudelemon.restaurand.app.activities.RRListActivity;

public class HelloFragment extends Fragment {

    public static HelloFragment newInstance() {
        final Bundle bundle = new Bundle();

        final HelloFragment fragment = new HelloFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    HelloFragmentCallbacks mCallbacks;

    // Container Activity must implement this interface
    public interface HelloFragmentCallbacks {
        public void onAddRRClicked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        ButterKnife.bind(this, rootView);

        TextView welcomeSub = (TextView) rootView.findViewById(R.id.welcomeSub);

        // query for all of the data objects in the database
        long numRows = App.db().countRestaurants();

        if (numRows < 1) {
            welcomeSub.setText(R.string.hello_no_rr);
        } else {
            StringBuilder sb = new StringBuilder()
                    .append(getString(R.string.welcome_1))
                    .append(numRows)
                    .append(getString(R.string.welcome_2));
            welcomeSub.setText(sb.toString());
        }

        return rootView;
    }

    @OnClick(R.id.welcomeRRbutton)
    public void showList() {
        final Intent intent = RRListActivity.makeIntent(getActivity());
        startActivity(intent);
    }

    @OnClick(R.id.addRRButton)
    public void addRestaurand() {
        mCallbacks.onAddRRClicked();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        final Activity activity = (Activity) context;
        try {
            mCallbacks = (HelloFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement HelloFragmentCallbacks");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
