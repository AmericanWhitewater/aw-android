package org.americanwhitewater.americanwhitewaterandroid.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.americanwhitewater.americanwhitewaterandroid.AWProvider;
import org.americanwhitewater.americanwhitewaterandroid.R;

import org.americanwhitewater.americanwhitewaterandroid.model.Filter;
import org.americanwhitewater.americanwhitewaterandroid.model.FilterManager;
import org.americanwhitewater.americanwhitewaterandroid.utility.ViewUtils;

import org.americanwhitewater.americanwhitewaterandroid.controller.FilterNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterContainer extends LinearLayout {
    private final FilterManager filterManager = AWProvider.Instance.getFilterManager();

    private FilterListener filterListener;
    private FilterNavigator.FilterViewState currentViewState;

    @BindView(R.id.toolbar_title) TextView toolbarTitle;
    @BindView(R.id.search_tap_target) LinearLayout searchTapTarget;
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

        void onClose();
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
        filterRegion.setFilter(filterManager.getFilter());
        filterDistance.initWithFilter(filterManager.getFilter());
        filterDifficulty.setFilter(filterManager.getFilter());
    }

    @OnClick(R.id.region_tab)
    protected void onRegionClicked() {
        if (currentViewState == FilterNavigator.FilterViewState.Region) {
            return;
        }

        searchTapTarget.setVisibility(VISIBLE);
        filterListener.onRegionSelected();
    }

    @OnClick(R.id.distance_tab)
    protected void onDistanceClicked() {
        if (currentViewState == FilterNavigator.FilterViewState.Distance) {
            return;
        }

        searchTapTarget.setVisibility(GONE);
        searchEdit.setVisibility(GONE);
        toolbarTitle.setVisibility(VISIBLE);
        filterListener.onDistanceSelected();
    }

    @OnClick(R.id.difficulty_tab)
    protected void onDifficultyClicked() {
        if (currentViewState == FilterNavigator.FilterViewState.Difficulty) {
            return;
        }

        searchTapTarget.setVisibility(GONE);
        searchEdit.setVisibility(GONE);
        toolbarTitle.setVisibility(VISIBLE);
        filterListener.onDifficultySelected();
    }

    @OnClick(R.id.close_tap_target)
    protected void onClose() {
        filterRegion.closeKeyboard();

        if (filterListener != null) {
            filterManager.setFilter(getFilter());
            filterManager.save();

            filterListener.onClose();
        }
    }

    @OnClick(R.id.search_tap_target)
    protected void onSearchClick() {
        searchTapTarget.setVisibility(GONE);
        toolbarTitle.setVisibility(GONE);
        searchEdit.setVisibility(VISIBLE);
        searchEdit.requestFocus();
        ViewUtils.showKeyboard(getContext(), searchEdit);
    }

    public Filter getFilter() {
        if (currentViewState != null) {
            filterManager.setFilter(getCurrentFilter(currentViewState));
            filterManager.save();
        }

        return filterManager.getFilter();
    }

    public void showViewState(FilterNavigator.FilterViewState viewState) {
        // Update the filter
        if (currentViewState != null) {
            filterManager.setFilter(getCurrentFilter(currentViewState));
            filterManager.save();
        }

        if (currentViewState == FilterNavigator.FilterViewState.Region) {
            filterRegion.closeKeyboard();
        }

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

                filterDistance.setFilterAndRefresh(filterManager.getFilter());
                break;
            case Difficulty:
                filterRegion.setVisibility(INVISIBLE);
                filterDistance.setVisibility(INVISIBLE);
                filterDifficulty.setVisibility(VISIBLE);
                break;
        }
    }

    private void updateTabUI(FilterNavigator.FilterViewState viewState) {
        setHighlighted(viewState == FilterNavigator.FilterViewState.Region, regionTab, regionTabHighlight);
        setHighlighted(viewState == FilterNavigator.FilterViewState.Distance, distanceTab, distanceTabHighlight);
        setHighlighted(viewState == FilterNavigator.FilterViewState.Difficulty, difficultyTab, difficultyTabHighlight);
    }

    private void setHighlighted(boolean isHighlighted, TextView tabText, View tabHighlight) {
        if (isHighlighted) {
            tabText.setAlpha(ViewConstants.ENABLED_ALPHA);
            tabHighlight.setVisibility(VISIBLE);

        } else {
            tabText.setAlpha(ViewConstants.DISABLED_ALPHA);
            tabHighlight.setVisibility(INVISIBLE);
        }
    }

    private Filter getCurrentFilter(FilterNavigator.FilterViewState viewState) {
        switch (viewState) {
            case Region:
                return filterRegion.getFilter();
            case Distance:
                return filterDistance.getFilter();
            case Difficulty:
                return filterDifficulty.getFilter();
        }

        return null;
    }
}
