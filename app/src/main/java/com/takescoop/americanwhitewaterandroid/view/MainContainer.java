package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.AboutNavigator;
import com.takescoop.americanwhitewaterandroid.controller.FilterNavigator;
import com.takescoop.americanwhitewaterandroid.controller.MainNavigator;
import com.takescoop.americanwhitewaterandroid.controller.NavigationDrawerActivity;
import com.takescoop.americanwhitewaterandroid.controller.RunDetailsNavigator;
import com.takescoop.americanwhitewaterandroid.model.Article;
import com.takescoop.americanwhitewaterandroid.model.Gage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainContainer extends RelativeLayout {
    private ActionBar actionBar;

    // Cache the browse map view
    private BrowseMapView browseMapView;

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

        // Swallow clicks
        modalContainer.setOnClickListener(v -> { });
    }

    ///////////////////////////////////////////////////////////////////////////
    // Container
    ///////////////////////////////////////////////////////////////////////////

    public void show(MainNavigator.ViewState viewState) {
        switch (viewState) {
            case News:
                break;

            case Article:
                throw new IllegalArgumentException("Use the method with dependencies");

            case RunsList:
                throw new IllegalArgumentException("Use the method with dependencies");

            case GageDetails:
                throw new IllegalArgumentException("Use the method with dependencies");

            case RunDetails:
                throw new IllegalArgumentException("Use the method with dependencies");

            case Favorites:
                throw new IllegalArgumentException("Use the method with dependencies");

            case Map:
                throw new IllegalArgumentException("Use the method with dependencies");

            case Filter:
                throw new IllegalArgumentException("Use the method with dependencies");

            case Search:
                throw new IllegalArgumentException("Use the method with dependencies");
            case About:
                break;
            case Team:
                break;
        }
    }

    public void showNewsFeed(NewsFeedView.NewsFeedListener listener) {
        NewsFeedView view = new NewsFeedView(getContext());
        view.setListener(listener);

        getTabContainer().removeAllViews();
        getTabContainer().addView(view);

        hideModal();
        actionBar.show();
        mainTabView.setViewState(MainTabView.TabViewState.News);
    }

    public void showSearchView(SearchView.SearchListener listener) {
        SearchView searchView = new SearchView(getContext());
        searchView.setListener(listener);
        getModalContainer().removeAllViews();
        getModalContainer().addView(searchView);

        showModal();
        actionBar.hide();
    }

    public void showBrowseMapView(BrowseMapView.BrowseMapListener listener) {
        if (browseMapView == null) {
            browseMapView = new BrowseMapView(getContext());
        }

        browseMapView.setListener(listener);
        browseMapView.updateReachesAndMap();
        getTabContainer().removeAllViews();
        getTabContainer().addView(browseMapView);

        hideModal();
        actionBar.show();
        mainTabView.setViewState(MainTabView.TabViewState.Map);
    }

    public FilterNavigator showFilterView(FilterNavigator.FilterNavigatorParentListener parentListener) {
        FilterNavigator filterNavigator = new FilterNavigator(modalContainer, parentListener);

        showModal();
        actionBar.hide();

        return filterNavigator;
    }

    public AboutNavigator showAboutView(AboutNavigator.AboutNavigatorParentListener listener) {
        AboutNavigator aboutNavigator = new AboutNavigator(modalContainer, listener);

        showModal();
        actionBar.hide();

        return aboutNavigator;
    }

    public void showRunsList(RunsView.RunsListener runsListener) {
        RunsView runsView = new RunsView(tabContainer.getContext());
        runsView.setRunsListener(runsListener);

        tabContainer.removeAllViews();
        tabContainer.addView(runsView);

        hideModal();
        actionBar.show();
        mainTabView.setViewState(MainTabView.TabViewState.Runs);
    }

    public void showFavoritesList(RunsView.RunsListener runsListener) {
        FavoritesView favoritesView = new FavoritesView(tabContainer.getContext());
        favoritesView.setRunsListener(runsListener);

        tabContainer.removeAllViews();
        tabContainer.addView(favoritesView);

        hideModal();
        actionBar.show();
        mainTabView.setViewState(MainTabView.TabViewState.Favorites);
    }

    public RunDetailsNavigator showRunDetails(int reachId, RunDetailsNavigator.RunDetailsParentListener listener) {
        getTabContainer().removeAllViews();
        RunDetailsNavigator runDetailsNavigator = new RunDetailsNavigator(tabContainer, reachId, listener);
        
        hideModal();
        actionBar.hide();
        mainTabView.setViewState(MainTabView.TabViewState.Runs);

        return runDetailsNavigator;
    }

    public void showGageDetails(Gage gage, GageView.GageViewListener listener) {
        tabContainer.removeAllViews();
        tabContainer.addView(new GageView(tabContainer.getContext(), gage, listener));

        hideModal();
        actionBar.hide();
        mainTabView.setViewState(MainTabView.TabViewState.Runs);
    }

    public void showTeam(TeamView.TeamViewListener listener) {
        modalContainer.removeAllViews();
        modalContainer.addView(new TeamView(getContext(), listener));

        showModal();
        actionBar.hide();
    }

    public void showArticle(Article article, ArticleView.ArticleViewListener listener) {
        modalContainer.removeAllViews();
        modalContainer.addView(new ArticleView(getContext(), article, listener));

        showModal();
        actionBar.hide();
        mainTabView.setViewState(MainTabView.TabViewState.News);
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
        if (getContext() instanceof NavigationDrawerActivity) {
            ((NavigationDrawerActivity) getContext()).setNavDrawerEnabled(false);
        }

        modalContainer.setVisibility(VISIBLE);
    }

    public void hideModal() {
        if (getContext() instanceof NavigationDrawerActivity) {
            ((NavigationDrawerActivity) getContext()).setNavDrawerEnabled(true);
        }

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
