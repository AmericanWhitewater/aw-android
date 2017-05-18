package com.takescoop.americanwhitewaterandroid.controller;

import java.util.Stack;

public class RunDetailsNavigator extends Navigator<RunDetailsNavigator.ViewState> {
    private Stack<ViewState> backstack = new Stack<>();
    
    public enum ViewState {
        Details, Map;
    }

    @Override public ViewState getDefaultViewState() {
        return ViewState.Details;
    }
}
