package com.takescoop.americanwhitewaterandroid.controller;

import android.support.v7.app.ActionBar;
import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.view.MainContainer;
import com.takescoop.americanwhitewaterandroid.view.MainTabView;

import java.util.Stack;

public class MainNavigator extends Navigator<MainNavigator.ViewState> implements MainTabView.TabListener, FilterNavigator.FilterNavigatorParentListener {
    private Stack<ViewState> backstack = new Stack<>();
    private final MainContainer mainContainer;

    public MainNavigator(ViewGroup container, ActionBar actionBar) {
        super(container);

        mainContainer = new MainContainer(container.getContext(), actionBar, this);
        container.removeAllViews();
        container.addView(mainContainer);

        pushAndShowViewState(ViewState.Runs);
    }

    public enum ViewState {
        News,
        Runs,
        Favorites,
        Map,
        Filter,
        Search;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Navigator
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void showViewState(ViewState viewState) {
        if (viewState == ViewState.Runs) {
            RunsNavigator runsNavigator = mainContainer.showRunsView();
            setChildNavigator(runsNavigator);
        } else if (viewState == ViewState.Filter) {
            FilterNavigator filterNavigator = mainContainer.showFilterView(this);
            setChildNavigator(filterNavigator);
        } else {
            mainContainer.show(viewState);
        }
    }

    @Override
    public void goBackToViewState(ViewState viewState) {
        //TODO
        showViewState(viewState);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    @Override public void onClose(Filter filter) {
        showViewState(popViewState());
        // TODO
    }

    @Override public void onNewsClicked() {
        pushAndShowViewState(ViewState.News);
    }

    @Override public void onRunsClicked() {
        pushAndShowViewState(ViewState.Runs);
    }

    @Override public void onFavoritesClicked() {
        pushAndShowViewState(ViewState.Favorites);
    }

    @Override public void onMapClicked() {
        pushAndShowViewState(ViewState.Map);
    }
}
