package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.MapViewActivity;
import com.takescoop.americanwhitewaterandroid.model.Rapid;
import com.takescoop.americanwhitewaterandroid.model.Reach;
import com.takescoop.americanwhitewaterandroid.utility.AWIntent;
import com.takescoop.americanwhitewaterandroid.utility.MapUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReachMapView extends FrameLayout implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private static final String TAG = ReachMapView.class.getSimpleName();

    // Default to US at the bottom of the map
    private static final LatLng MAP_DEFAULT_CENTER = new LatLng(56.09024, -95.712891);
    private static final int MIN_ZOOM = 15; // Google zoom level
    private static final int MAP_PADDING_dp = 70;

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

        this.map.clear();

        this.map.setInfoWindowAdapter(new ReachInfoWindowAdapter());
        this.map.setOnInfoWindowClickListener(this);

        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(MAP_DEFAULT_CENTER, 3.0f));

        display(reach, map);
    }

    @Override public void onInfoWindowClick(Marker marker) {
        AWIntent.goToDirections(getContext(), marker.getPosition());
    }

    private void display(@Nullable Reach reach, @Nullable GoogleMap map) {
        if (reach == null || map == null) {
            return;
        }

        List<Marker> markers = Lists.newArrayList();

        MarkerOptions putin = getMarker(MarkerType.PutIn, reach.getPutinLatLng());
        if (putin != null) {
            Marker marker = map.addMarker(putin);
            marker.setSnippet("River Put-in");
            markers.add(marker);
        }

        MarkerOptions takeout = getMarker(MarkerType.TakeOut, reach.getTakeoutLatLng());
        if (takeout != null) {
            Marker marker = map.addMarker(takeout);
            marker.setSnippet("River Take-out");
            markers.add(marker);
        }

        if (reach.getGage() != null) {
            MarkerOptions gage = getMarker(MarkerType.Gauge, reach.getGage().getLocation());
            if (gage != null) {
                Marker marker = map.addMarker(gage);
                marker.setSnippet("Gauge");
                markers.add(marker);
            }
        }

        for (Rapid rapid : reach.getRapids()) {
            MarkerOptions rapidMarker = getMarker(MarkerType.Rapid, rapid.getLocation());
            if (rapidMarker != null) {
                Marker marker = map.addMarker(rapidMarker);

                if (!TextUtils.isEmpty(rapid.getName())) {
                    marker.setSnippet(rapid.getName());
                } else {
                    marker.setSnippet("Rapid");
                }
                markers.add(marker);
            }
        }

        MapUtils.zoomToMarkers(getContext(), map, markers, MAP_PADDING_dp, MIN_ZOOM);
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

    private class ReachInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        @Override public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override public View getInfoContents(Marker marker) {
            MapInfoWindowView view = new MapInfoWindowView(getContext());

            String title = marker.getSnippet();
            view.display(title, reach.getName());

            return view;
        }
    }
}
