package com.takescoop.americanwhitewaterandroid.controller;

import java.util.Stack;

public class FilterNavigator {
    private Stack<ViewState> backstack = new Stack<>();
    
    public enum ViewState {
        Region, Distance, Difficulty;
    }


    public ViewState getCurrentViewState() {
        if (backstack.empty()) {
            return ViewState.Region;
        } else {
            return backstack.peek();
        }
    }

    public ViewState goToNextViewState() {
        return ViewState.Region;
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
