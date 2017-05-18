package com.takescoop.americanwhitewaterandroid.controller;

import com.takescoop.americanwhitewaterandroid.view.MainTabView;
import com.takescoop.americanwhitewaterandroid.view.RunsView;

import java.util.Stack;

public class MainNavigator extends Navigator<MainNavigator.ViewState> implements MainTabView.TabListener, RunsView.RunsListener {
    private Stack<ViewState> backstack = new Stack<>();
    private MainContainer mainContainer;

    @Override public void onReachClick(int reachId) {

    }

    public enum ViewState {
        News,
        Runs,
        Favorites,
        Map,
        Filter,
        Search,
        Gage,
        RunDetails;
    }

    public MainNavigator(MainContainer mainContainer) {
        this.mainContainer = mainContainer;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Navigator
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public ViewState getDefaultViewState() {
        return ViewState.Runs;
    }

    @Override
    public ViewState goToViewState(ViewState viewState) {
        super.goToViewState(viewState);

        if(viewState == ViewState.Runs) {
            mainContainer.showRunsView(this);
        } else {
            mainContainer.show(viewState);
        }

        return viewState;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////

    @Override public void onNewsClicked() {
        goToViewState(ViewState.News);
    }

    @Override public void onRunsClicked() {
        goToViewState(ViewState.Runs);
    }

    @Override public void onFavoritesClicked() {
        goToViewState(ViewState.Favorites);
    }

    @Override public void onMapClicked() {
        goToViewState(ViewState.Map);
    }
}
