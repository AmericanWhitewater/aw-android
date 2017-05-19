package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.FilterNavigator;
import com.takescoop.americanwhitewaterandroid.controller.FilterVC;
import com.takescoop.americanwhitewaterandroid.controller.MainNavigator;
import com.takescoop.americanwhitewaterandroid.controller.RunsNavigator;

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

        LayoutInflater.from(context).inflate(R.layout.view_main, this);
        onFinishInflate();

        mainTabView.setTabListener(tabListener);
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
                throw new IllegalArgumentException("Use the method with dependencies");

            case Search:
                getModalContainer().removeAllViews();
                getModalContainer().addView(new SearchView(getContext()));

                showModal();
                actionBar.hide();
                break;
        }
    }

    public FilterNavigator showFilterView(FilterNavigator.FilterNavigatorParentListener parentListener) {
        FilterNavigator filterNavigator = new FilterNavigator(modalContainer, parentListener);

        showModal();
        actionBar.hide();

        return filterNavigator;
    }

    public RunsNavigator showRunsView() {
        RunsNavigator runsNavigator = new RunsNavigator(tabContainer);
        // TODO action bar.

        hideModal();
        actionBar.show();

        return runsNavigator;
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
