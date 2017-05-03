package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.ButterKnife;

public class ReachMapView extends LinearLayout {
    public ReachMapView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_reach_map, this);
        onFinishInflate();
    }

    public ReachMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_reach_map, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }
}
