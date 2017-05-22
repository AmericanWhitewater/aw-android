package com.takescoop.americanwhitewaterandroid.controller;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import java.util.Stack;

public abstract class Navigator<T extends Enum<?>> {
    private Stack<T> backstack = new Stack<T>();
    ViewGroup container;

    private Navigator childNavigator;

    public abstract void showViewState(T viewState);
    public abstract void goBackToViewState(T viewState);

    public Navigator(ViewGroup container) {
        this.container = container;
    }

    @Nullable
    public T getCurrentViewState() {
        if (backstack.empty()) {
            return null;
        } else {
            return backstack.peek();
        }
    }

    public T pushViewState(T viewState) {
        backstack.push(viewState);

        return viewState;
    }

    public void pushAndShowViewState(T viewState) {
        pushViewState(viewState);
        showViewState(viewState);
    }

    // Returns the state to go back to, or null if there isn't one.
    public T popViewState() {
        if (backstack.empty()) {
            return null;
        }

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
            T viewState = popViewState();
            if (viewState != null) {
                goBackToViewState(viewState);
                return BackEventResult.Handled;
            } else {
                return BackEventResult.NotHandled;
            }
        }
    }
}
