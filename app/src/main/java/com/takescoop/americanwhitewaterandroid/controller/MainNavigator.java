package com.takescoop.americanwhitewaterandroid.controller;

import android.support.v7.app.ActionBar;
import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.view.MainContainer;
import com.takescoop.americanwhitewaterandroid.view.MainTabView;
import com.takescoop.americanwhitewaterandroid.view.RunsView;

import java.util.Stack;

public class MainNavigator extends Navigator<MainNavigator.ViewState> implements MainTabView.TabListener {
    private Stack<ViewState> backstack = new Stack<>();
    private final MainContainer mainContainer;

    public MainNavigator(ViewGroup container, ActionBar actionBar) {
        super(container);

        mainContainer = new MainContainer(container.getContext(), actionBar, this);
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
