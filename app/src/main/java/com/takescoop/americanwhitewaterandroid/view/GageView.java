package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GageView extends LinearLayout {
    @BindView(R.id.gage_cell) GageCell gageCell;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.reach_list) RecyclerView reachList;

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
