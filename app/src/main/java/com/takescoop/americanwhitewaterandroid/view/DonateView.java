package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DonateView extends LinearLayout {

    public DonateView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_donate, this);
        onFinishInflate();
    }

    public DonateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_donate, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    @OnClick(R.id.donate_button)
    protected void onDonateClick() {
        // TODO
    }
}
