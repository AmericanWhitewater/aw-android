package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.MapViewActivity;
import com.takescoop.americanwhitewaterandroid.model.AWRegion;
import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.model.FilterManager;
import com.takescoop.americanwhitewaterandroid.model.FlowLevel;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;
import com.takescoop.americanwhitewaterandroid.utility.AWIntent;
import com.takescoop.americanwhitewaterandroid.utility.MapUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableSingleObserver;

public class BrowseMapView extends LinearLayout implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private static final String TAG = BrowseMapView.class.getSimpleName();
    private static final int MIN_ZOOM = 15; // Google zoom level
    private static final int MAP_PADDING_dp = 60;

    private final FilterManager filterManager = AWProvider.Instance.getFilterManager();

    private List<ReachSearchResult> reachSearchResults;
    private GoogleMap map;

    @BindView(R.id.map_container) FrameLayout mapContainer;

    private enum MarkerType {
        Runnable(R.drawable.map_marker_green),
        Low(R.drawable.map_marker_yellow),
        High(R.drawable.map_marker_red),
        Frozen(R.drawable.map_marker_blue),
        NoInfo(R.drawable.map_marker_grey);

        private int drawableId;

        MarkerType(int drawableId) {
            this.drawableId = drawableId;
        }

        public int getDrawableId() {
            return drawableId;
        }

        public static MarkerType markerTypeForFlow(FlowLevel flowLevel) {
            switch (flowLevel) {
                case Low:
                    return Low;
                case Runnable:
                    return Runnable;
                case High:
                    return High;
                case Frozen:
                    return Frozen;
                case NoInfo:
                    return NoInfo;
            }
            return null;
        }
    }

    public BrowseMapView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_map, this);
        onFinishInflate();
    }

    public BrowseMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_map, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        if (getContext() instanceof MapViewActivity) {
            SupportMapFragment mapFragment = ((MapViewActivity) getContext()).putMapFragmentInContainer(mapContainer);
            mapFragment.getMapAsync(this);
        }

        updateReaches(filterManager.getFilter());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        this.map.setInfoWindowAdapter(new BrowseInfoWindowAdapter());
        this.map.setOnInfoWindowClickListener(this);

        display(reachSearchResults, map);
    }

    @Override public void onInfoWindowClick(Marker marker) {
        AWIntent.goToDirections(getContext(), marker.getPosition());
    }

    public void setReachSearchResults(List<ReachSearchResult> reachSearchResults) {
        this.reachSearchResults = reachSearchResults;

        display(reachSearchResults, map);
    }

    private void updateReaches(Filter filter) {
        AWApi.Instance.getReaches(filter).subscribe(new DisposableSingleObserver<List<ReachSearchResult>>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull List<ReachSearchResult> reachSearchResults) {
                setReachSearchResults(reachSearchResults);
            }

            @Override public void onError(@io.reactivex.annotations.NonNull Throwable e) {
            }
        });
    }

    private void display(@Nullable List<ReachSearchResult> results, @Nullable GoogleMap map) {
        if (results == null || map == null) {
            return;
        }

        map.clear();
        
        List<Marker> markers = Lists.newArrayList();

        for (ReachSearchResult result : results) {
            if (result.getPutInLatLng() != null) {
                MarkerType markerType = MarkerType.markerTypeForFlow(result.getFlowLevel());
                MarkerOptions markerOptions = getMarker(markerType, result.getPutInLatLng());
                Marker marker = map.addMarker(markerOptions);
                marker.setTag(result);

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

    private class BrowseInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        @Override public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override public View getInfoContents(Marker marker) {
            MapInfoWindowView view = new MapInfoWindowView(getContext());

            ReachSearchResult result = (ReachSearchResult) marker.getTag();
            view.display(result);

            return view;
        }
    }
}
