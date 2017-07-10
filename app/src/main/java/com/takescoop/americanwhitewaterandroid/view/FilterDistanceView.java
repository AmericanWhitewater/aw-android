package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.LocationProviderActivity;
import com.takescoop.americanwhitewaterandroid.model.FilterManager;
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
import io.reactivex.subjects.SingleSubject;

public class FilterDistanceView extends LinearLayout {
    private static final int SLIDER_MAX = 100;

    private final FilterManager filterManager = AWProvider.Instance.getFilterManager();
    private LatLng currentLocation;

    @BindView(R.id.filter_distance_slider) SeekBar slider;
    @BindView(R.id.slider_value) TextView sliderValue;
    @BindView(R.id.filter_distance_address1) TextView address1;
    @BindView(R.id.filter_distance_address2) TextView address2;
//    @BindView(R.id.filter_distance_new_address) TextView newAddressButton;
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

        init();
    }

    private void init() {
        slider.setMax(SLIDER_MAX);
        updateSliderValue(0);
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateSliderValue(progress);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        updateLocation();
    }

    @Nullable
    public LatLngBounds getBounds() {
        if (currentLocation == null || getSliderValue() <= 0) {
            return null;
        }

        return MapUtils.getBoundsFromRadius(currentLocation, getSliderValue());
    }

    @OnClick(R.id.filter_distance_update_location)
    protected void updateLocation() {
        if (getContext() instanceof LocationProviderActivity) {
            LocationProviderActivity locationActivity = (LocationProviderActivity) getContext();
            SingleSubject<LatLng> locationObservable = SingleSubject.create();
            locationObservable.subscribe(new DisposableSingleObserver<LatLng>() {
                @Override public void onSuccess(@NonNull LatLng latLng) {
                    currentLocation = latLng;

                    updateAddress(latLng);
                }

                @Override public void onError(@NonNull Throwable e) {
                    Dialogs.toast(e.getMessage());
                }
            });

            locationActivity.getCurrentLocation(locationObservable);
        }
    }

    private void updateSliderValue(int progress) {
        sliderValue.setText(getSliderValue(progress) + " mi");
    }

    private void updateAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // 1 is the max addresses returned

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String locality = !TextUtils.isEmpty(address.getLocality()) ? address.getLocality() : address.getSubLocality();
                String longAddressString = address.getAddressLine(0) + ", " + address.getAdminArea() + ", " + address.getPostalCode();
                address1.setText(locality + ", " + address.getAdminArea());
                address2.setText(longAddressString);
            }

        } catch (IOException e) {

        }
    }

    // In miles
    private int getSliderValue() {
        return getSliderValue(slider.getProgress());
    }

    private int getSliderValue(int progress) {
        return 2 * progress;
    }
}
