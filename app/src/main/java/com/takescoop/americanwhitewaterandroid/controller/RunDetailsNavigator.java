package com.takescoop.americanwhitewaterandroid.controller;

import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.view.ReachView;

public class RunDetailsNavigator extends Navigator<RunDetailsNavigator.ReachViewState> implements ReachView.RunDetailsListener {
    ReachView reachView;

    public enum ReachViewState {
        Details, Map;
    }

    public RunDetailsNavigator(ViewGroup container, int reachId) {
        super(container);

        reachView = new ReachView(container.getContext(), this, reachId);
        reachView.showViewState(ReachViewState.Details);

        container.removeAllViews();
        container.addView(reachView);
    }

    @Override public void showViewState(ReachViewState viewState) {
        switch (viewState) {

        }
    }

    @Override public void goBackToViewState(ReachViewState viewState) {
        showViewState(viewState);
    }

    @Override public void onDetailsClicked() {
        reachView.showViewState(ReachViewState.Details);
    }

    @Override public void onMapClicked() {
        reachView.showViewState(ReachViewState.Map);
    }
}
