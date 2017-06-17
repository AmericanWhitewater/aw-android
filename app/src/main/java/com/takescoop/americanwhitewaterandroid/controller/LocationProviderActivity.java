package com.takescoop.americanwhitewaterandroid.controller;

import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Single;

public interface LocationProviderActivity {
    Single<LatLng> getCurrentLocation();
}
