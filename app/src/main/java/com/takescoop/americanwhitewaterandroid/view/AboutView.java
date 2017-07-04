package com.takescoop.americanwhitewaterandroid.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.AboutNavigator;
import com.takescoop.americanwhitewaterandroid.model.api.Urls;
import com.takescoop.americanwhitewaterandroid.utility.AWIntent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutView extends ScrollView {
    @BindView(R.id.logo) ImageView logo;
    @BindView(R.id.detail) TextView detail;
    @BindView(R.id.donate_button) LinearLayout donateButton;
    @BindView(R.id.website_button) TextView websiteButton;
    @BindView(R.id.read_more) TextView readMore;

    public AboutView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_about_detail, this);
        onFinishInflate();
    }

    public AboutView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_about_detail, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        readMore.setOnClickListener(v -> AWIntent.goToUrl(getContext(), Urls.ABOUT_URL));
    }

    public void showViewState(AboutNavigator.ViewState viewState) {
        switch (viewState) {
            case Mission:
                logo.setVisibility(VISIBLE);
                detail.setText(getContext().getString(R.string.about_mission));
                donateButton.setVisibility(GONE);
                websiteButton.setVisibility(VISIBLE);
                websiteButton.setText(getContext().getString(R.string.about_join));
                websiteButton.setOnClickListener(v -> {
                    AWIntent.goToUrl(getContext(), Urls.JOIN_URL);
                });
                break;
            case Stewardship:
                logo.setVisibility(GONE);
                detail.setText(getContext().getString(R.string.about_stewardship));
                donateButton.setVisibility(GONE);
                websiteButton.setVisibility(VISIBLE);
                websiteButton.setText(getContext().getString(R.string.about_stewardship_learn_more));
                websiteButton.setOnClickListener(v -> {
                    AWIntent.goToUrl(getContext(), Urls.STEWARDSHIP_URL);
                });
                break;
            case Funding:
                logo.setVisibility(GONE);
                detail.setText(getContext().getString(R.string.about_funding));
                donateButton.setVisibility(VISIBLE);
                donateButton.setOnClickListener(v -> DonateView.getDonateMenu((Activity) getContext()).show());
                websiteButton.setVisibility(GONE);
                break;
        }
    }
}
