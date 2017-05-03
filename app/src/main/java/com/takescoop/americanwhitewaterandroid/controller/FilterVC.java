package com.takescoop.americanwhitewaterandroid.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.view.FilterDifficultyView;
import com.takescoop.americanwhitewaterandroid.view.FilterDistanceView;
import com.takescoop.americanwhitewaterandroid.view.FilterRegionView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.DISABLED_ALPHA;
import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.ENABLED_ALPHA;

public class FilterVC extends LinearLayout {
    private FilterListener filterListener;

    @BindView(R.id.region_tab) TextView regionTab;
    @BindView(R.id.distance_tab) TextView distanceTab;
    @BindView(R.id.difficulty_tab) TextView difficultyTab;
    @BindView(R.id.filter_tabs) LinearLayout filterTabs;
    @BindView(R.id.region_tab_highlight) View regionTabHighlight;
    @BindView(R.id.distance_tab_highlight) View distanceTabHighlight;
    @BindView(R.id.difficulty_tab_highlight) View difficultyTabHighlight;
    @BindView(R.id.container) FrameLayout container;

    public enum FilterViewState {
        Region, Distance, Difficulty;
    }

    public interface FilterListener {
        void onClose();
    }

    public FilterVC(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_filter, this);
        onFinishInflate();
    }

    public FilterVC(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_filter, this);
    }

    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }

    @OnClick(R.id.region_tab)
    protected void onRegionClicked() {
        showViewState(FilterViewState.Region);
    }

    @OnClick(R.id.distance_tab)
    protected void onDistanceClicked() {
        showViewState(FilterViewState.Distance);
    }

    @OnClick(R.id.difficulty_tab)
    protected void onDifficultylicked(){
        showViewState(FilterViewState.Difficulty);
    }

    @OnClick(R.id.close)
    protected void onClose() {
        if (filterListener != null) {
            filterListener.onClose();
        }
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        showViewState(FilterViewState.Region);
    }

    private void showViewState(FilterViewState viewState) {
        updateTabUI(viewState);

        switch (viewState) {
            case Region:
                container.removeAllViews();
                container.addView(new FilterRegionView(getContext()));
                break;
            case Distance:
                container.removeAllViews();
                container.addView(new FilterDistanceView(getContext()));
                break;
            case Difficulty:
                container.removeAllViews();
                container.addView(new FilterDifficultyView(getContext()));
                break;
        }
    }

    private void updateTabUI(FilterViewState viewState) {
        setHighlighted(viewState == FilterViewState.Region, regionTab, regionTabHighlight);
        setHighlighted(viewState == FilterViewState.Distance, distanceTab, distanceTabHighlight);
        setHighlighted(viewState == FilterViewState.Difficulty, difficultyTab, difficultyTabHighlight);
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
