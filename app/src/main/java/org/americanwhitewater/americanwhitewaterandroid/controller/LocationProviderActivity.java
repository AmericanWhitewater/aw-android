package org.americanwhitewater.americanwhitewaterandroid.controller;

import com.google.android.gms.maps.model.LatLng;

import io.reactivex.subjects.SingleSubject;

public interface LocationProviderActivity {
    void getCurrentLocation(SingleSubject<LatLng> observable);
}
