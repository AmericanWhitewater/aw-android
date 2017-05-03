package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterRegionView extends LinearLayout {
    @BindView(R.id.fr_current_location) TextView currentLocationText;
    @BindView(R.id.fr_list) ListView list;

    public FilterRegionView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_filter_region, this);
        onFinishInflate();
    }

    public FilterRegionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_filter_region, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }
}
