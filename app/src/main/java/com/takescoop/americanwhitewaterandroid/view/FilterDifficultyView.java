package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.ButterKnife;

public class FilterDifficultyView extends LinearLayout {
    public FilterDifficultyView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_filter_difficulty, this);
        onFinishInflate();
    }

    public FilterDifficultyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_filter_difficulty, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }
}
