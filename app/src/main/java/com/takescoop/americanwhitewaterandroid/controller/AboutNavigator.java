package com.takescoop.americanwhitewaterandroid.controller;

import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.view.AboutContainer;

public class AboutNavigator extends Navigator<AboutNavigator.ViewState> implements AboutContainer.AboutListener {
    private final AboutNavigatorParentListener listener;
    private final AboutContainer aboutContainer;

    public interface AboutNavigatorParentListener {
        void onClose();
    }

    public enum ViewState {
        Mission, Stewardship, Funding
    }

    public AboutNavigator(ViewGroup container, AboutNavigatorParentListener listener) {
        super(container);

        this.listener = listener;
        this.aboutContainer = new AboutContainer(container.getContext(), this);

        container.removeAllViews();
        container.addView(aboutContainer);

        pushAndShowViewState(ViewState.Mission);
    }

    @Override public void showViewState(ViewState viewState) {
        aboutContainer.showViewState(viewState);
    }

    @Override public void goBackToViewState(ViewState viewState) {
        aboutContainer.showViewState(viewState);
    }


    @Override public void onMissionSelected() {
        if (getCurrentViewState() != ViewState.Mission) {
            pushAndShowViewState(ViewState.Mission);
        }
    }

    @Override public void onStewardshipSelected() {
        if (getCurrentViewState() != ViewState.Stewardship) {
            pushAndShowViewState(ViewState.Stewardship);
        }
    }

    @Override public void onFundingSelected() {
        if (getCurrentViewState() != ViewState.Funding) {
            pushAndShowViewState(ViewState.Funding);
        }
    }

    @Override public void onClose() {
        onBack();
    }

    @Override
    public BackEventResult onBack() {
        BackEventResult result = super.onBack();
        if (result == BackEventResult.NotHandled) {
            listener.onClose();
        }

        return BackEventResult.Handled;
    }
}
