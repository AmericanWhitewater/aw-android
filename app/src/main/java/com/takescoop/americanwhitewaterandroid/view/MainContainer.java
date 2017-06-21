package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.FilterNavigator;
import com.takescoop.americanwhitewaterandroid.controller.MainNavigator;
import com.takescoop.americanwhitewaterandroid.controller.RunDetailsNavigator;
import com.takescoop.americanwhitewaterandroid.model.Gage;

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

            case RunsList:
                throw new IllegalArgumentException("Use the method with dependencies");

            case GageDetails:
                throw new IllegalArgumentException("Use the method with dependencies");

            case RunDetails:
                throw new IllegalArgumentException("Use the method with dependencies");

            case Favorites:
                break;

            case Map:
                getTabContainer().removeAllViews();
                getTabContainer().addView(new BrowseMapView(getContext()));

                hideModal();
                actionBar.show();
                break;

            case Filter:
                throw new IllegalArgumentException("Use the method with dependencies");

            case Search:
                throw new IllegalArgumentException("Use the method with dependencies");
        }
    }

    public void showSearchView(SearchView.SearchListener listener) {
        SearchView searchView = new SearchView(getContext());
        searchView.setListener(listener);
        getModalContainer().removeAllViews();
        getModalContainer().addView(searchView);

        showModal();
        actionBar.hide();
    }

    public FilterNavigator showFilterView(FilterNavigator.FilterNavigatorParentListener parentListener) {
        FilterNavigator filterNavigator = new FilterNavigator(modalContainer, parentListener);

        showModal();
        actionBar.hide();

        return filterNavigator;
    }

    public void showRunsList(RunsView.RunsListener runsListener) {
        RunsView runsView = new RunsView(tabContainer.getContext());
        runsView.setRunsListener(runsListener);

        tabContainer.removeAllViews();
        tabContainer.addView(runsView);

        hideModal();
        actionBar.show();
    }

    public RunDetailsNavigator showRunDetails(int reachId, RunDetailsNavigator.RunDetailsParentListener listener) {
        RunDetailsNavigator runDetailsNavigator = new RunDetailsNavigator(tabContainer, reachId, listener);

        hideModal();
        actionBar.hide();

        return runDetailsNavigator;
    }

    public void showGageDetails(Gage gage) {
        tabContainer.removeAllViews();
        tabContainer.addView(new GageView(tabContainer.getContext(), gage));

        hideModal();
        actionBar.hide();
    }

    ///////////////////////////////////////////////////////////////////////////
    // View
    ///////////////////////////////////////////////////////////////////////////

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
