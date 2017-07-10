package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.FilterNavigator.FilterViewState;
import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.utility.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.DISABLED_ALPHA;
import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.ENABLED_ALPHA;

public class FilterContainer extends LinearLayout {
    private FilterListener filterListener;
    private FilterViewState currentViewState;

    @BindView(R.id.toolbar_title) TextView toolbarTitle;
    @BindView(R.id.search) ImageView search;
    @BindView(R.id.search_edit) EditText searchEdit;

    @BindView(R.id.region_tab) TextView regionTab;
    @BindView(R.id.distance_tab) TextView distanceTab;
    @BindView(R.id.difficulty_tab) TextView difficultyTab;
    @BindView(R.id.filter_tabs) LinearLayout filterTabs;
    @BindView(R.id.region_tab_highlight) View regionTabHighlight;
    @BindView(R.id.distance_tab_highlight) View distanceTabHighlight;
    @BindView(R.id.difficulty_tab_highlight) View difficultyTabHighlight;

    @BindView(R.id.filter_region) FilterRegionView filterRegion;
    @BindView(R.id.filter_distance) FilterDistanceView filterDistance;
    @BindView(R.id.filter_difficulty) FilterDifficultyView filterDifficulty;

    public interface FilterListener {
        void onRegionSelected();

        void onDistanceSelected();

        void onDifficultySelected();

        void onClose(Filter filter);
    }

    public FilterContainer(Context context, FilterListener listener) {
        super(context);

        this.filterListener = listener;

        LayoutInflater.from(context).inflate(R.layout.view_filter, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        filterRegion.setSearchEdit(searchEdit);
    }

    @OnClick(R.id.region_tab)
    protected void onRegionClicked() {
        if (currentViewState == FilterViewState.Region) {
            return;
        }

        search.setVisibility(VISIBLE);
        filterListener.onRegionSelected();
    }

    @OnClick(R.id.distance_tab)
    protected void onDistanceClicked() {
        if (currentViewState == FilterViewState.Distance) {
            return;
        }

        search.setVisibility(GONE);
        searchEdit.setVisibility(GONE);
        toolbarTitle.setVisibility(VISIBLE);
        filterListener.onDistanceSelected();
    }

    @OnClick(R.id.difficulty_tab)
    protected void onDifficultyClicked() {
        if (currentViewState == FilterViewState.Difficulty) {
            return;
        }

        search.setVisibility(GONE);
        searchEdit.setVisibility(GONE);
        toolbarTitle.setVisibility(VISIBLE);
        filterListener.onDifficultySelected();
    }

    @OnClick(R.id.close)
    protected void onClose() {
        if (filterListener != null) {
            filterListener.onClose(getFilter());
        }
    }

    @OnClick(R.id.search)
    protected void onSearchClick() {
        search.setVisibility(GONE);
        toolbarTitle.setVisibility(GONE);
        searchEdit.setVisibility(VISIBLE);
        searchEdit.requestFocus();
        ViewUtils.showKeyboard(getContext(), searchEdit);
    }

    public Filter getFilter() {
        Filter filter = new Filter();
        filter.setRegions(filterRegion.getSelectedRegions());

        filter.setBounds(filterDistance.getBounds());

        filter.setDifficultyLowerBound(filterDifficulty.getLowerBound());
        filter.setDifficultyUpperBound(filterDifficulty.getUpperBound());

        return filter;
    }

    public void showViewState(FilterViewState viewState) {
        currentViewState = viewState;

        updateTabUI(viewState);

        switch (viewState) {
            case Region:
                filterRegion.setVisibility(VISIBLE);
                filterDistance.setVisibility(INVISIBLE);
                filterDifficulty.setVisibility(INVISIBLE);
                break;
            case Distance:
                filterRegion.setVisibility(INVISIBLE);
                filterDistance.setVisibility(VISIBLE);
                filterDifficulty.setVisibility(INVISIBLE);
                break;
            case Difficulty:
                filterRegion.setVisibility(INVISIBLE);
                filterDistance.setVisibility(INVISIBLE);
                filterDifficulty.setVisibility(VISIBLE);
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
