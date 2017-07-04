package com.takescoop.americanwhitewaterandroid.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class AWIntent {

    /**
     * Opens a google map that shows directions from your current location to the provided address.
     *
     * @param context
     * @param latLng
     */
    public static void goToDirections(Context context, LatLng latLng) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + latLng.latitude + "," + latLng.longitude));
        context.startActivity(intent);
    }

    public static void goToUrl(final Context context, String urlString) {
        Intent urlIntent = new Intent(Intent.ACTION_VIEW);
        urlIntent.setData(Uri.parse(urlString));
        context.startActivity(urlIntent);
    }
}
