package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.ButterKnife;

public class ReachDetailView extends LinearLayout {
    public ReachDetailView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_reach_details, this);
    }

    public ReachDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_reach_details, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }
}
