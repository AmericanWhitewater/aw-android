package com.takescoop.americanwhitewaterandroid.controller;

import java.util.Stack;

public class RunDetailsNavigator {
    private Stack<ViewState> backstack = new Stack<>();
    
    public enum ViewState {
        Details, Map;
    }

    public ViewState getCurrentViewState() {
        if (backstack.empty()) {
            return ViewState.Details;
        } else {
            return backstack.peek();
        }
    }

    public ViewState goToNextViewState() {
        return ViewState.Details;
    }

    // Returns the state to go back to, or null if there isn't one.
    public ViewState goToLastViewState() {
        backstack.pop();

        if (backstack.empty()) {
            return null;
        } else {
            return getCurrentViewState();
        }
    }
}
