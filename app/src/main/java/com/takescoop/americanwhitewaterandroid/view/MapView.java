package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.ButterKnife;

public class MapView extends LinearLayout {
    public MapView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_map, this);
        onFinishInflate();
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_map, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }
}
