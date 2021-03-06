package org.americanwhitewater.americanwhitewaterandroid.utility;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

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

    public static void zoomToMarkers(Context context, GoogleMap map, @Nullable List<Marker> markers, int padding_dp, int minZoom) {
        if (markers == null || markers.size() <= 0) {
            return;
        }

        final LatLngBounds bounds = getBounds(markers);
        try {
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, pixelsFromDP(padding_dp, (Activity) context)), new GoogleMap.CancelableCallback() {
                @Override public void onFinish() {

                    // Zoom back out if too close
                    if (map.getCameraPosition().zoom > minZoom) {
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(markers.get(0).getPosition()).zoom(minZoom).build();
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }

                @Override public void onCancel() {

                }
            });

        // Hack, the bounds method requires waiting for layout and for some reason the on global layout listener isn't doing the trick.  So, use the non-bounds method instead.
        } catch (IllegalStateException e) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(markers.get(0).getPosition()).zoom(minZoom).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

    private static LatLngBounds getBounds(@NonNull List<Marker> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
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
