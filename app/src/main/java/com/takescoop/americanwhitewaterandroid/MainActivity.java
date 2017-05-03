package com.takescoop.americanwhitewaterandroid;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.takescoop.americanwhitewaterandroid.controller.BackEventResult;
import com.takescoop.americanwhitewaterandroid.controller.MainContainer;
import com.takescoop.americanwhitewaterandroid.controller.MainNavigator;
import com.takescoop.americanwhitewaterandroid.model.AWRegion;
import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.model.Reach;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;
import com.takescoop.americanwhitewaterandroid.view.MainView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainNavigator mainNavigator;

    @BindView(R.id.main_view) MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        initNavDrawer(toolbar);
        MainContainer mainContainer = new MainContainer(this, getSupportActionBar(), mainView);
        mainNavigator = new MainNavigator(mainContainer);
        mainView.setTabListener(mainNavigator);
        mainNavigator.goToViewState(MainNavigator.ViewState.Runs);

        Filter filter = new Filter();
        filter.addRegion(AWRegion.Kansas);
        AWApi.Instance.getReaches(filter).subscribe(new DisposableSingleObserver<List<ReachSearchResult>>() {
            @Override public void onSuccess(@NonNull List<ReachSearchResult> reachSearchResults) {
                Log.w("test", reachSearchResults.toString());
            }

            @Override public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError " + e);
            }
        });

        AWApi.Instance.getReach(10386).subscribe(new DisposableSingleObserver<Reach>() {
            @Override public void onSuccess(@NonNull Reach reach) {
                Log.w("test", reach.toString());
            }

            @Override public void onError(@NonNull Throwable e) {
                Log.e("test", e.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            BackEventResult result = mainNavigator.onBack();
            if (result == BackEventResult.NotHandled) {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            mainNavigator.goToViewState(MainNavigator.ViewState.Search);
            return true;
        } else if (id == R.id.action_filter) {
            mainNavigator.goToViewState(MainNavigator.ViewState.Filter);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initNavDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
