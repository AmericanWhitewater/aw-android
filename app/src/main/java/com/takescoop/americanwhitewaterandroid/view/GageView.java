package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.ButterKnife;

public class GageView extends LinearLayout {
    public GageView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_gage, this);
        onFinishInflate();
    }

    public GageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_gage, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }
}
