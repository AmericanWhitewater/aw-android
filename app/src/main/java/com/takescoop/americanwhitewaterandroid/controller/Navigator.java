package com.takescoop.americanwhitewaterandroid.controller;

import java.util.Stack;

public abstract class Navigator<T extends Enum<?>> {
    private Stack<T> backstack = new Stack<T>();

    private Navigator childNavigator;

    public abstract T getDefaultViewState();

    public T getCurrentViewState() {
        if (backstack.empty()) {
            return getDefaultViewState();
        } else {
            return backstack.peek();
        }
    }

    public T goToViewState(T viewState) {
        backstack.push(viewState);

        return viewState;
    }

    // Returns the state to go back to, or null if there isn't one.
    public T goToLastViewState() {
        backstack.pop();

        if (backstack.empty()) {
            return null;
        } else {
            return getCurrentViewState();
        }
    }

    public void setChildNavigator(Navigator childNavigator) {
        this.childNavigator = childNavigator;
    }

    public BackEventResult onBack() {
        BackEventResult result = null;

        // Check child first
        if (childNavigator != null) {
            result = childNavigator.onBack();
        }

        if (result == BackEventResult.Handled) {
            return BackEventResult.Handled;

        // If child hasn't handled the back event, handle it in this navigator.
        } else {
            return goToLastViewState() != null ? BackEventResult.Handled : BackEventResult.NotHandled;
        }
    }
}
