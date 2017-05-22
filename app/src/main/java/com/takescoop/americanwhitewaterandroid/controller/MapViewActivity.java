package com.takescoop.americanwhitewaterandroid.controller;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

public interface MapViewActivity {
    SupportMapFragment putMapFragmentInContainer(FrameLayout frameLayout);
}
