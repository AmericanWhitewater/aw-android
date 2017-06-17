package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.LocationProviderActivity;
import com.takescoop.americanwhitewaterandroid.utility.Dialogs;
import com.takescoop.americanwhitewaterandroid.utility.MapUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.observers.DisposableSingleObserver;

public class FilterDistanceView extends LinearLayout {
    private LatLng currentLocation;

    @BindView(R.id.filter_distance_slider) SeekBar slider;
    @BindView(R.id.filter_distance_address) TextView addressText;
    @BindView(R.id.filter_distance_new_address) TextView newAddressButton;
    @BindView(R.id.filter_distance_update_location) TextView updateLocationButton;

    public FilterDistanceView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_filter_distance, this);
        onFinishInflate();
    }

    public FilterDistanceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_filter_distance, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    @Nullable
    public LatLngBounds getBounds() {
        if (currentLocation == null) {
            return null;
        }

        return MapUtils.getBoundsFromRadius(currentLocation, getSliderValue());
    }

    @OnClick(R.id.filter_distance_update_location)
    protected void updateLocation() {
        if (getContext() instanceof LocationProviderActivity) {
            LocationProviderActivity locationActivity = (LocationProviderActivity) getContext();
            locationActivity.getCurrentLocation().subscribe(new DisposableSingleObserver<LatLng>() {
                @Override public void onSuccess(@NonNull LatLng latLng) {
                    currentLocation = latLng;

                    updateAddress(latLng);
                }

                @Override public void onError(@NonNull Throwable e) {
                    Dialogs.toast(e.getMessage());
                }
            });
        }
    }

    private void updateAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // 1 is the max addresses returned

            if (addresses != null && addresses.size() > 0) {
                String addressString = addresses.get(0).getAddressLine(0) + "\n" + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                addressText.setText(addressString);
            }

        } catch (IOException e) {

        }
    }

    // In miles
    private int getSliderValue() {
        return 100;
    }
}
