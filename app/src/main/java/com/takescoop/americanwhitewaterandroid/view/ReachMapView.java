package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.MapViewActivity;
import com.takescoop.americanwhitewaterandroid.model.Reach;

import butterknife.ButterKnife;

public class ReachMapView extends FrameLayout implements OnMapReadyCallback {
    private static final String TAG = ReachMapView.class.getSimpleName();

    public ReachMapView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_reach_map, this);
        onFinishInflate();
    }

    public ReachMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_reach_map, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        if (getContext() instanceof MapViewActivity) {
            SupportMapFragment mapFragment = ((MapViewActivity) getContext()).putMapFragmentInContainer(this);
            mapFragment.getMapAsync(this);
        }
    }

    public void showReach(Reach reach) {

    }

    @Override public void onMapReady(GoogleMap googleMap) {
        Log.e(TAG, "onMapReady ");
    }
}
