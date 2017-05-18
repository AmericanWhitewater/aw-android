package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.FilterVC;
import com.takescoop.americanwhitewaterandroid.controller.MainNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainContainer extends RelativeLayout {
    private ActionBar actionBar;

    @BindView(R.id.tab_container) FrameLayout tabContainer;
    @BindView(R.id.main_tab_view) MainTabView mainTabView;
    @BindView(R.id.modal_container) FrameLayout modalContainer;

    public MainContainer(Context context, ActionBar actionBar, MainTabView.TabListener tabListener) {
        super(context);

        this.actionBar = actionBar;
        mainTabView.setTabListener(tabListener);

        LayoutInflater.from(context).inflate(R.layout.view_main, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Container
    ///////////////////////////////////////////////////////////////////////////

    public void show(MainNavigator.ViewState viewState) {
        switch (viewState) {
            case News:
                getTabContainer().removeAllViews();
                getTabContainer().addView(new NewsFeedView(getContext()));

                hideModal();
                actionBar.show();
                break;

            case Runs:
                throw new IllegalArgumentException("Use the method with dependencies");

            case Favorites:
                break;

            case Map:
                getTabContainer().removeAllViews();
                getTabContainer().addView(new MapView(getContext()));

                hideModal();
                actionBar.show();
                break;

            case Filter:
                getModalContainer().removeAllViews();
                getModalContainer().addView(new FilterVC(getContext()));

                showModal();
                actionBar.hide();
                break;

            case Search:
                getModalContainer().removeAllViews();
                getModalContainer().addView(new SearchView(getContext()));

                showModal();
                actionBar.hide();
                break;

            case Gage:
                getTabContainer().removeAllViews();
                getTabContainer().addView(new SearchView(getContext()));

                hideModal();
                actionBar.show();
                break;

            case RunDetails:
                getTabContainer().removeAllViews();
                getTabContainer().addView(new ReachView(getContext()));

                hideModal();
                actionBar.show();
                break;
        }
    }

    public void showRunsView(RunsView.RunsListener runsListener) {
        RunsView runsView = new RunsView(getContext());
        runsView.setRunsListener(runsListener);

        getTabContainer().removeAllViews();
        getTabContainer().addView(runsView);

        hideModal();
        actionBar.show();
    }

    public FrameLayout getTabContainer() {
        return tabContainer;
    }

    public FrameLayout getModalContainer() {
        return modalContainer;
    }

    public void showModal() {
        modalContainer.setVisibility(VISIBLE);
    }

    public void hideModal() {
        modalContainer.setVisibility(GONE);
    }

    public void setTabViewVisible(boolean isVisible) {
        if (isVisible) {
            mainTabView.setVisibility(VISIBLE);
        } else {
            mainTabView.setVisibility(GONE);
        }
    }
}
