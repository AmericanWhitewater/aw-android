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

public class SearchView extends LinearLayout {
    @BindView(R.id.back) ImageView back;
    @BindView(R.id.clear) ImageView clear;
    @BindView(R.id.toolbar) LinearLayout toolbar;
    @BindView(R.id.search_list) RecyclerView searchList;

    public SearchView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_search, this);
        onFinishInflate();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_search, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }
}
