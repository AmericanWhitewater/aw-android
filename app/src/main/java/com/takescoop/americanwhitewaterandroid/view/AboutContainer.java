package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.AboutNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.DISABLED_ALPHA;
import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.ENABLED_ALPHA;

public class AboutContainer extends LinearLayout {
    private final AboutListener aboutListener;

    @BindView(R.id.close) ImageView close;
    @BindView(R.id.toolbar) LinearLayout toolbar;
    @BindView(R.id.mission_tab) TextView missionTab;
    @BindView(R.id.stewardship_tab) TextView stewardshipTab;
    @BindView(R.id.funding_tab) TextView fundingTab;
    @BindView(R.id.mission_tab_highlight) View missionTabHighlight;
    @BindView(R.id.stewardship_tab_highlight) View stewardshipTabHighlight;
    @BindView(R.id.funding_tab_highlight) View fundingTabHighlight;
    @BindView(R.id.about_view) AboutView aboutView;

    public interface AboutListener {
        void onMissionSelected();

        void onStewardshipSelected();

        void onFundingSelected();

        void onCloseClicked();
    }

    public AboutContainer(Context context, AboutListener aboutListener) {
        super(context);
        this.aboutListener = aboutListener;

        LayoutInflater.from(context).inflate(R.layout.view_about_container, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    @OnClick(R.id.mission_tab)
    protected void onMissionSelected() {
        aboutListener.onMissionSelected();
    }

    @OnClick(R.id.stewardship_tab)
    protected void onStewardshipSelected() {
        aboutListener.onStewardshipSelected();
    }

    @OnClick(R.id.funding_tab)
    protected void onFundingClicked() {
        aboutListener.onFundingSelected();
    }

    @OnClick(R.id.close)
    protected void onCloseClicked() {
        aboutListener.onCloseClicked();
    }

    public void showViewState(AboutNavigator.ViewState viewState) {
        updateTabUI(viewState);

        aboutView.showViewState(viewState);
    }

    private void updateTabUI(AboutNavigator.ViewState viewState) {
        setHighlighted(viewState == AboutNavigator.ViewState.Mission, missionTab, missionTabHighlight);
        setHighlighted(viewState == AboutNavigator.ViewState.Stewardship, stewardshipTab, stewardshipTabHighlight);
        setHighlighted(viewState == AboutNavigator.ViewState.Funding, fundingTab, fundingTabHighlight);
    }

    private void setHighlighted(boolean isHighlighted, TextView tabText, View tabHighlight) {

        if (isHighlighted) {
            tabText.setAlpha(ENABLED_ALPHA);
            tabHighlight.setVisibility(VISIBLE);

        } else {
            tabText.setAlpha(DISABLED_ALPHA);
            tabHighlight.setVisibility(INVISIBLE);
        }
    }
}
