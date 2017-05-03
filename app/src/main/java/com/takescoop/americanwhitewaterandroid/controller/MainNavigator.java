package com.takescoop.americanwhitewaterandroid.controller;

import com.takescoop.americanwhitewaterandroid.view.MainTabView;

import java.util.Stack;

public class MainNavigator implements MainTabView.TabListener {
    private Stack<ViewState> backstack = new Stack<>();
    private MainContainer mainContainer;
    public enum ViewState {
        News, Runs, Favorites, Map, Filter, Search, Gage, RunDetails;
    }

    public MainNavigator(MainContainer mainContainer) {
        this.mainContainer = mainContainer;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Navigator
    ///////////////////////////////////////////////////////////////////////////
    public ViewState getCurrentViewState() {
        if (backstack.empty()) {
            return ViewState.Runs;
        } else {
            return backstack.peek();
        }
    }

    public ViewState goToViewState(ViewState viewState) {
        backstack.push(viewState);
        mainContainer.show(viewState);

        return viewState;
    }

    // Returns the state to go back to, or null if there isn't one.
    public ViewState goToLastViewState() {
        backstack.pop();

        if (backstack.empty()) {
            return null;
        } else {
            ViewState viewState = getCurrentViewState();
            mainContainer.show(viewState);
            return viewState;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////
    public BackEventResult onBack() {
        return goToLastViewState() != null ? BackEventResult.Handled : BackEventResult.NotHandled;
    }

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
