package com.takescoop.americanwhitewaterandroid.controller;

import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.view.FilterContainer;

public class FilterNavigator extends Navigator<FilterNavigator.FilterViewState> implements FilterContainer.FilterListener{
    private FilterNavigatorParentListener parentListener;
    private final FilterContainer filterContainer;

    public interface FilterNavigatorParentListener {
        void onClose(Filter filter);
    }

    public enum FilterViewState {
        Region, Distance, Difficulty;
    }

    public FilterNavigator(ViewGroup container, FilterNavigatorParentListener parentListener) {
        super(container);

        this.parentListener = parentListener;
        filterContainer = new FilterContainer(container.getContext(), this);

        container.removeAllViews();
        container.addView(filterContainer);

        pushAndShowViewState(FilterViewState.Region);
    }

    @Override public void onRegionSelected() {
        pushAndShowViewState(FilterViewState.Region);
    }

    @Override public void onDistanceSelected() {
        pushAndShowViewState(FilterViewState.Distance);
    }

    @Override public void onDifficultySelected() {
        pushAndShowViewState(FilterViewState.Difficulty);
    }

    @Override public void onClose(Filter filter) {
        parentListener.onClose(filter);
    }

    @Override
    public void showViewState(FilterViewState viewState) {
        filterContainer.showViewState(viewState);
    }

    @Override
    public void goBackToViewState(FilterViewState viewState) {
        showViewState(viewState);
    }

    @Override
    public BackEventResult onBack() {
        BackEventResult result = super.onBack();
        if (result == BackEventResult.NotHandled) {
            parentListener.onClose(filterContainer.getFilter());
        }

        return BackEventResult.Handled;
    }
}
