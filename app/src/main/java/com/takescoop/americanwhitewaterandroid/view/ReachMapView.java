package com.takescoop.americanwhitewaterandroid.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.MapViewActivity;
import com.takescoop.americanwhitewaterandroid.model.Rapid;
import com.takescoop.americanwhitewaterandroid.model.Reach;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReachMapView extends FrameLayout implements OnMapReadyCallback {
    private static final String TAG = ReachMapView.class.getSimpleName();
    private static final int MIN_ZOOM = 15; // Google zoom level
    private static final int MAP_PADDING_dp = 60;

    private Reach reach;
    private GoogleMap map;

    @BindView(R.id.map_container) FrameLayout mapContainer;

    private enum MarkerType {
        PutIn(R.drawable.map_marker_green),
        TakeOut(R.drawable.map_marker_blue),
        Gauge(R.drawable.map_marker_red),
        Rapid(R.drawable.map_marker_yellow);

        private int drawableId;

        MarkerType(int drawableId) {
            this.drawableId = drawableId;
        }

        public int getDrawableId() {
            return drawableId;
        }
    }

    public ReachMapView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_reach_map, this);
        onFinishInflate();
    }

    public ReachMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_reach_map, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        if (getContext() instanceof MapViewActivity) {
            SupportMapFragment mapFragment = ((MapViewActivity) getContext()).putMapFragmentInContainer(mapContainer);
            mapFragment.getMapAsync(this);
        }
    }

    public void showReach(Reach reach) {
        this.reach = reach;

        display(reach, map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        display(reach, map);
    }

    private void display(@Nullable Reach reach, @Nullable GoogleMap map) {
        if (reach == null || map == null) {
            return;
        }

        List<MarkerOptions> markers = Lists.newArrayList();

        MarkerOptions putin =  getMarker(MarkerType.PutIn, reach.getPutinLatLng());
        if (putin != null) {
            markers.add(putin);
        }
        MarkerOptions takeout =  getMarker(MarkerType.TakeOut, reach.getTakeoutLatLng());
        if (takeout != null) {
            markers.add(takeout);
        }
        if (reach.getGage() != null) {
            MarkerOptions gage =  getMarker(MarkerType.Gauge, reach.getGage().getLocation());
            if (gage != null) {
                markers.add(gage);
            }
        }

        for (Rapid rapid : reach.getRapids()) {
            MarkerOptions rapidMarker =  getMarker(MarkerType.Rapid, rapid.getLocation());
            if (rapidMarker != null) {
                markers.add(rapidMarker);
            }
        }

        for (MarkerOptions markerOptions : markers) {
            map.addMarker(markerOptions);
        }

        zoomToMarkers(markers);
    }

    private void zoomToMarkers(List<MarkerOptions> markers) {
        if (markers.size() > 0) {
            final LatLngBounds bounds = getBounds(markers);
            try {
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, pixelsFromDP(MAP_PADDING_dp, (Activity) getContext()))); // map padding

                // Hack, the bounds method requires waiting for layout and for some reason the on global layout listener isn't doing the trick.  So, use the non-bounds method instead.
            } catch (IllegalStateException e) {
                CameraPosition cameraPosition = new CameraPosition.Builder().target(markers.get(0).getPosition()).zoom(MIN_ZOOM).build();
                map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }

        if (map.getCameraPosition().zoom > MIN_ZOOM) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(markers.get(0).getPosition()).zoom(MIN_ZOOM).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private LatLngBounds getBounds(List<MarkerOptions> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MarkerOptions marker : markers) {
            builder.include(marker.getPosition());
        }
        return builder.build();
    }

    private MarkerOptions getMarker(MarkerType markerType, @Nullable LatLng latLng) {
        if (latLng == null) {
            return null;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(getContext(), markerType.getDrawableId()).getCurrent();
        Bitmap bitmap = Bitmap.createBitmap(bitmapDrawable.getBitmap());
        return new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .position(latLng);
    }

    private static int pixelsFromDP(int dp, Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return (int) Math.ceil(dp * logicalDensity);
    }
}
