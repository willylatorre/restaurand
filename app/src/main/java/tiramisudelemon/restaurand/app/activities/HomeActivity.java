package tiramisudelemon.restaurand.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import tiramisudelemon.restaurand.app.R;
import tiramisudelemon.restaurand.app.fragments.HelloFragment;
import tiramisudelemon.restaurand.app.fragments.RRListFragment;


public class HomeActivity extends AppCompatActivity implements HelloFragment.HelloFragmentCallbacks {

    private static final String EXTRA_FRAGMENT_NUMBER = "fragmentNumber";
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_RESTS = 1;


    public static Intent makeIntent(Context context){
        return makeIntent(context, FRAGMENT_HOME);
    }

    public static Intent makeIntent(Context context, int fragmentNumber){
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(EXTRA_FRAGMENT_NUMBER, fragmentNumber);
        return intent;
    }

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectItemFromDrawer(menuItem);
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        final int fragment_number = getIntent().getIntExtra(EXTRA_FRAGMENT_NUMBER, 0);
        initFragment(fragment_number);
    }

    private void initFragment(int fragment_number) {

        final Fragment fragment;
        switch (fragment_number){
            case FRAGMENT_RESTS:
                fragment = RRListFragment.newInstance();
                break;
            case FRAGMENT_HOME:
                fragment = HelloFragment.newInstance();
                break;
            default:
                fragment = HelloFragment.newInstance();
                break;
        }

        setFragment(fragment);
    }

    private void selectItemFromDrawer(MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.drawer_home:
                fragment = HelloFragment.newInstance();
                break;
            case R.id.drawer_favourite:
                fragment = RRListFragment.newInstance();
                break;
        }

        // update the main content by replacing fragments
        setFragment(fragment);
    }


    @Override
    public void onAddRRClicked() {
        final Intent intent = AddRRPlaceActivity.makeIntent(this);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("main")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
