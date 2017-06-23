package com.takescoop.americanwhitewaterandroid.controller;

import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.model.Gage;
import com.takescoop.americanwhitewaterandroid.view.ReachView;

public class RunDetailsNavigator extends Navigator<RunDetailsNavigator.ReachViewState> implements ReachView.RunDetailsListener {
    ReachView reachView;
    private final RunDetailsParentListener listener;

    public enum ReachViewState {
        Details, Map;
    }

    public interface RunDetailsParentListener {
        void onGageSelected(Gage gage);

        void onClose();
    }

    public RunDetailsNavigator(ViewGroup container, int reachId, RunDetailsParentListener listener) {
        super(container);

        this.listener = listener;
        reachView = new ReachView(container.getContext(), this, reachId);
        reachView.showViewState(ReachViewState.Details);
        pushViewState(ReachViewState.Details);

        container.removeAllViews();
        container.addView(reachView);
    }

    @Override public void showViewState(ReachViewState viewState) {
        switch (viewState) {
            case Details:
                reachView.showViewState(ReachViewState.Details);
                break;
            case Map:
                reachView.showViewState(ReachViewState.Map);
                break;
        }
    }

    @Override public void goBackToViewState(ReachViewState viewState) {
        showViewState(viewState);
    }

    @Override public void onDetailsClicked() {
        pushAndShowViewState(ReachViewState.Details);
    }

    @Override public void onMapClicked() {
        pushAndShowViewState(ReachViewState.Map);
    }

    @Override public void onGageSelected(Gage gage) {
        listener.onGageSelected(gage);
    }

    @Override public void onClose() {
        listener.onClose();
    }
}
