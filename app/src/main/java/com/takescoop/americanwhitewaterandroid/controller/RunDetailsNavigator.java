package com.takescoop.americanwhitewaterandroid.controller;

import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.view.ReachView;

import java.util.Stack;

public class RunDetailsNavigator extends Navigator<RunDetailsNavigator.ViewState> {
    private Stack<ViewState> backstack = new Stack<>();
    ReachView reachView;

    public RunDetailsNavigator(ViewGroup container, int reachId) {
        super(container);

        reachView = new ReachView(container.getContext(), reachId);
    }

    public enum ViewState {
        Details, Map;
    }

    @Override public void showViewState(ViewState viewState) {
        switch (viewState) {

        }
    }

    @Override public void goBackToViewState(ViewState viewState) {
        showViewState(viewState);
    }
}
