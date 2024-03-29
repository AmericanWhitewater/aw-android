package org.americanwhitewater.americanwhitewaterandroid.view;

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
import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.controller.LocationProviderActivity;
import org.americanwhitewater.americanwhitewaterandroid.model.Filter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.subjects.SingleSubject;

public class FilterDistanceView extends LinearLayout {
    private static final int SLIDER_MAX = 100;

    private Filter filter;
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
    }

    public void initWithFilter(Filter filter) {
        this.filter = filter;

        slider.setMax(SLIDER_MAX);
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateSliderValue(getSliderValue_miles(progress));
                filter.setRadius(getRadius());
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setFilterAndRefresh(filter);
    }

    public void setFilterAndRefresh(Filter filter) {
        this.filter = filter;

        setSliderValue(filter.getRadius());
        updateLocation();
    }

    public Filter getFilter() {
        return filter;
    }

    private int getRadius() {
        return getSliderValue_miles();
    }

    @Nullable
    public LatLng getCurrentLocation() {
        return currentLocation;
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

                }
            });

            locationActivity.getCurrentLocation(locationObservable);
        }
    }

    private void updateSliderValue(int miles) {
        sliderValue.setText(miles + " mi");
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

    private void setSliderValue(int miles) {
        slider.setProgress(miles / 2);
        updateSliderValue(miles);
    }

    // In miles
    private int getSliderValue_miles() {
        return getSliderValue_miles(slider.getProgress());
    }

    private int getSliderValue_miles(int progress) {
        return 2 * progress;
    }
}
