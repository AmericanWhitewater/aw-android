package com.takescoop.americanwhitewaterandroid.utility;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

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
}
