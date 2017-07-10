package com.takescoop.americanwhitewaterandroid.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.takescoop.americanwhitewaterandroid.AWApplication;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;

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

    public static void goToEmail(final Context context, String subject, String emailText) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("message/rfc822");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.putExtra(Intent.EXTRA_TEXT, emailText);
        context.startActivity(sendIntent);
    }

    public static void goToRateApp(final Context context) {
        final Uri uri = Uri.parse("market://details?id=" + AWApplication.getContext().getPackageName());
        final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);

        if (context.getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0)
        {
            context.startActivity(rateAppIntent);
        }
    }
}
