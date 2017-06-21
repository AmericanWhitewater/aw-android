package com.takescoop.americanwhitewaterandroid.utility;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapUtils {
    private static final double EARTH_RADIUS_mi = 3959;

    public static LatLngBounds getBoundsFromRadius(LatLng latLng, double radius_mi) {
        GeoLocation center = GeoLocation.fromDegrees(latLng.latitude, latLng.longitude);
        GeoLocation[] corners = center.boundingCoordinates(radius_mi, EARTH_RADIUS_mi);

        return new LatLngBounds(toLatLng(corners[0]), toLatLng(corners[1]));
    }

    private static LatLng toLatLng(GeoLocation geoLocation) {
        return new LatLng(geoLocation.getLatitudeInDegrees(), geoLocation.getLongitudeInDegrees());
    }

    public static void zoomToMarkers(Context context, GoogleMap map, List<MarkerOptions> markers, int padding_dp, int minZoom) {
        if (markers.size() > 0) {
            final LatLngBounds bounds = getBounds(markers);
            try {
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, pixelsFromDP(padding_dp, (Activity) context))); // map padding

                // Hack, the bounds method requires waiting for layout and for some reason the on global layout listener isn't doing the trick.  So, use the non-bounds method instead.
            } catch (IllegalStateException e) {
                CameraPosition cameraPosition = new CameraPosition.Builder().target(markers.get(0).getPosition()).zoom(minZoom).build();
                map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }

        if (map.getCameraPosition().zoom > minZoom) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(markers.get(0).getPosition()).zoom(minZoom).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private static LatLngBounds getBounds(List<MarkerOptions> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MarkerOptions marker : markers) {
            builder.include(marker.getPosition());
        }
        return builder.build();
    }

    private static int pixelsFromDP(int dp, Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return (int) Math.ceil(dp * logicalDensity);
    }
}
