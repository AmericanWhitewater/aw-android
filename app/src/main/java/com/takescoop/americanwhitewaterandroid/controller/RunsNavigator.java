package com.takescoop.americanwhitewaterandroid.controller;

import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.view.GageView;
import com.takescoop.americanwhitewaterandroid.view.RunsView;

public class RunsNavigator extends Navigator<RunsNavigator.RunsViewState> implements RunsView.RunsListener {

    public enum RunsViewState {
        RunsList, GageDetails, RunDetails;
    }

    public RunsNavigator(ViewGroup container) {
        super(container);

        pushAndShowViewState(RunsViewState.RunsList);
    }

    @Override
    public void showViewState(RunsViewState viewState) {
        switch (viewState) {
            case RunsList:

                RunsView runsView = new RunsView(container.getContext());
                runsView.setRunsListener(this);

                container.removeAllViews();
                container.addView(runsView);
                break;

            case GageDetails:
                container.removeAllViews();
                container.addView(new GageView(container.getContext()));

                break;

            case RunDetails:
                throw new IllegalArgumentException("Use the method with dependencies");
        }
    }

    @Override
    public void goBackToViewState(RunsViewState viewState) {
        showViewState(viewState);
    }

    @Override public void onReachClick(int reachId) {
        pushViewState(RunsViewState.RunDetails);
        showRunDetails(reachId);
    }

    private void showRunDetails(int reachId) {
        RunDetailsNavigator runDetailsNavigator = new RunDetailsNavigator(container, reachId);
        setChildNavigator(runDetailsNavigator);
    }
}
