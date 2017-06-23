package com.takescoop.americanwhitewaterandroid.controller;

import android.support.v7.app.ActionBar;
import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.model.Gage;
import com.takescoop.americanwhitewaterandroid.view.GageView;
import com.takescoop.americanwhitewaterandroid.view.MainContainer;
import com.takescoop.americanwhitewaterandroid.view.MainTabView;
import com.takescoop.americanwhitewaterandroid.view.RunsView;
import com.takescoop.americanwhitewaterandroid.view.SearchView;

import java.util.Stack;

public class MainNavigator extends Navigator<MainNavigator.ViewState> implements MainTabView.TabListener,
        FilterNavigator.FilterNavigatorParentListener, SearchView.SearchListener, RunsView.RunsListener,
        RunDetailsNavigator.RunDetailsParentListener, GageView.GageViewListener {
    private Stack<Integer> reachIds = new Stack<>(); // A bit of a hack to save for the backstack
    private final MainContainer mainContainer;

    public MainNavigator(ViewGroup container, ActionBar actionBar) {
        super(container);

        mainContainer = new MainContainer(container.getContext(), actionBar, this);
        container.removeAllViews();
        container.addView(mainContainer);

        pushAndShowViewState(ViewState.RunsList);
    }

    public enum ViewState {
        News,
        RunsList,
        GageDetails,
        RunDetails,
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
        if (viewState == ViewState.RunsList) {
            mainContainer.showRunsList(this);
        } else if (viewState == ViewState.Filter) {
            FilterNavigator filterNavigator = mainContainer.showFilterView(this);
            setChildNavigator(filterNavigator);
        } else if (viewState == ViewState.Search) {
            mainContainer.showSearchView(this);
        } else {
            mainContainer.show(viewState);
        }
    }

    @Override
    public void goBackToViewState(ViewState viewState) {
        if (viewState == ViewState.RunDetails) {
            Integer reachId = reachIds.pop(); // TODO
            mainContainer.showRunDetails(reachId, this);
        } else {
            showViewState(viewState);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    @Override public void onClose(Filter filter) {
        setChildNavigator(null);

        onBack();
    }

    @Override public void onNewsClicked() {
        pushAndShowViewState(ViewState.News);
    }

    @Override public void onRunsClicked() {
        pushAndShowViewState(ViewState.RunsList);
    }

    @Override public void onFavoritesClicked() {
        pushAndShowViewState(ViewState.Favorites);
    }

    @Override public void onMapClicked() {
        pushAndShowViewState(ViewState.Map);
    }

    @Override public void onReachSelected(int reachId) {
        reachIds.push(reachId);

        pushViewState(ViewState.RunDetails);
        RunDetailsNavigator runDetailsNavigator = mainContainer.showRunDetails(reachId, this);
        setChildNavigator(runDetailsNavigator);
    }

    @Override public void onGageSelected(Gage gage) {
        pushViewState(ViewState.GageDetails);
        mainContainer.showGageDetails(gage, this);
    }

    @Override public void onClose() {
        setChildNavigator(null);

        onBack();
    }
}
