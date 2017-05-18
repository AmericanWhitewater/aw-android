package com.takescoop.americanwhitewaterandroid.controller;

import android.view.ViewGroup;

import java.util.Stack;

public class RunDetailsNavigator extends Navigator<RunDetailsNavigator.ViewState> {
    private Stack<ViewState> backstack = new Stack<>();

    public RunDetailsNavigator(ViewGroup container) {
        super(container);
    }

    public enum ViewState {
        Details, Map;
    }

    @Override public ViewState getDefaultViewState() {
        return ViewState.Details;
    }
}
