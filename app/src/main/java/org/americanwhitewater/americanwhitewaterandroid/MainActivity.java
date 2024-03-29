package org.americanwhitewater.americanwhitewaterandroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView ;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import org.americanwhitewater.americanwhitewaterandroid.R;

import org.americanwhitewater.americanwhitewaterandroid.controller.BackEventResult;
import org.americanwhitewater.americanwhitewaterandroid.controller.LocationProviderActivity;
import org.americanwhitewater.americanwhitewaterandroid.controller.MainNavigator;
import org.americanwhitewater.americanwhitewaterandroid.controller.MapViewActivity;
import org.americanwhitewater.americanwhitewaterandroid.controller.NavigationDrawerActivity;
import org.americanwhitewater.americanwhitewaterandroid.utility.AWIntent;
import org.americanwhitewater.americanwhitewaterandroid.utility.Dialogs;
import org.americanwhitewater.americanwhitewaterandroid.utility.DisplayStringUtils;
import org.americanwhitewater.americanwhitewaterandroid.view.DonateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.subjects.SingleSubject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MapViewActivity, LocationProviderActivity, NavigationDrawerActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_LOCATION_CODE = 111; // Number to tag the request with.  Complete Android BS.
    private MainNavigator mainNavigator;

    private SingleSubject<LatLng> getLocationObservable;

    @BindView(R.id.container) FrameLayout container;
    @BindView(R.id.version) TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        initNavDrawer(toolbar);
        mainNavigator = new MainNavigator(container, getSupportActionBar());
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
            mainNavigator.pushAndShowViewState(MainNavigator.ViewState.Search);
            return true;
        } else if (id == R.id.action_filter) {
            mainNavigator.pushAndShowViewState(MainNavigator.ViewState.Filter);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.about) {
            mainNavigator.showAbout();
        } else if (id == R.id.team) {
            mainNavigator.showTeam();
        } else if (id == R.id.rate) {
            AWIntent.goToRateApp(this);
        } else if (id == R.id.feedback) {
            AWIntent.goToEmail(this, "evan@americanwhitewater.org");
        } else if (id == R.id.donate) {
            DonateView.getDonateMenu(this).show();
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
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        version.setText("v " + DisplayStringUtils.getVersionString(this));
    }

    @Override public void setNavDrawerEnabled(boolean isEnabled) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (isEnabled) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override public SupportMapFragment putMapFragmentInContainer(FrameLayout frameLayout) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        View view = mapFragment.getView();
        ViewGroup parent = (ViewGroup) view.getParent();
        parent.removeView(view);
        frameLayout.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return mapFragment;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Location
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void getCurrentLocation(SingleSubject<LatLng> observable) {
        if (getLocationObservable != null && getLocationObservable.hasObservers()) {
            getLocationObservable.onError(new IllegalStateException("New request taking precedence"));
        }
        getLocationObservable = observable;

        if (!areLocationPermissionsGranted(this)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
        } else {
            getAndReturnLocation(observable);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        boolean isPermissionGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (requestCode == REQUEST_LOCATION_CODE && isPermissionGranted && getLocationObservable != null) {
            getAndReturnLocation(getLocationObservable);
        } else {
            Dialogs.toast("Please enable location to continue.");
        }
    }

    @SuppressWarnings({"MissingPermission"})
    private void getAndReturnLocation(final SingleSubject<LatLng> observable) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location == null) {
            locationManager.requestLocationUpdates(provider, 0, 0, new LocationListener() {
                @Override public void onLocationChanged(Location location) {
                    locationManager.removeUpdates(this);

                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    observable.onSuccess(latLng);
                }

                @Override public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override public void onProviderEnabled(String provider) {

                }

                @Override public void onProviderDisabled(String provider) {

                }
            });

        } else {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            getLocationObservable.onSuccess(latLng);
            getLocationObservable = null;
        }
    }

    private boolean areLocationPermissionsGranted(Context context) {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isLocationManagerEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean areFinePermissionsGranted = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        return isLocationManagerEnabled && areFinePermissionsGranted;
    }
}
