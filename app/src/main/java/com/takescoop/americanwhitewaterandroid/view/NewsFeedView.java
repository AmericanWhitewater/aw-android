package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.ButterKnife;

public class NewsFeedView extends RelativeLayout {
    public NewsFeedView(Context context) {
        super(context);
        
        LayoutInflater.from(context).inflate(R.layout.view_news_feed, this);
        onFinishInflate();
    }

    public NewsFeedView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_news_feed, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }
}
