package com.takescoop.americanwhitewaterandroid.controller;

import android.support.v7.app.ActionBar;
import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.model.Article;
import com.takescoop.americanwhitewaterandroid.model.Gage;
import com.takescoop.americanwhitewaterandroid.view.ArticleView;
import com.takescoop.americanwhitewaterandroid.view.BrowseMapView;
import com.takescoop.americanwhitewaterandroid.view.GageView;
import com.takescoop.americanwhitewaterandroid.view.MainContainer;
import com.takescoop.americanwhitewaterandroid.view.MainTabView;
import com.takescoop.americanwhitewaterandroid.view.NewsFeedView;
import com.takescoop.americanwhitewaterandroid.view.RunsView;
import com.takescoop.americanwhitewaterandroid.view.SearchView;
import com.takescoop.americanwhitewaterandroid.view.TeamView;

import java.util.Stack;

public class MainNavigator extends Navigator<MainNavigator.ViewState> implements MainTabView.TabListener,
        FilterNavigator.FilterNavigatorParentListener, SearchView.SearchListener, RunsView.RunsListener, BrowseMapView.BrowseMapListener,
        RunDetailsNavigator.RunDetailsParentListener, GageView.GageViewListener, NewsFeedView.NewsFeedListener,
        AboutNavigator.AboutNavigatorParentListener, TeamView.TeamViewListener, ArticleView.ArticleViewListener {

    private Integer currentReachId;
    private Stack<Integer> reachIds = new Stack<>(); // A bit of a hack to save for the backstack
    private Stack<Gage> gages = new Stack<>();
    private final MainContainer mainContainer;

    public MainNavigator(ViewGroup container, ActionBar actionBar) {
        super(container);

        mainContainer = new MainContainer(container.getContext(), actionBar, this);
        container.removeAllViews();
        container.addView(mainContainer);

        pushAndShowViewState(ViewState.RunsList);
    }

    public enum ViewState {
        News,
        Article,
        RunsList,
        GageDetails,
        RunDetails,
        Favorites,
        Map,
        Filter,
        Search,
        About,
        Team;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Navigator
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public ViewState pushViewState(ViewState viewState) {
        if (getCurrentViewState() == ViewState.RunDetails) {
            reachIds.push(currentReachId);
        }

        return super.pushViewState(viewState);
    }

    @Override
    public void showViewState(ViewState viewState) {
        if (viewState == ViewState.RunsList) {
            mainContainer.showRunsList(this);
        } else if (viewState == ViewState.Map) {
            mainContainer.showBrowseMapView(this);
        } else if (viewState == ViewState.Favorites) {
            mainContainer.showFavoritesList(this);
        } else if (viewState == ViewState.Filter) {
            FilterNavigator filterNavigator = mainContainer.showFilterView(this);
            setChildNavigator(filterNavigator);
        } else if (viewState == ViewState.Search) {
            mainContainer.showSearchView(this);
        } else if (viewState == ViewState.News) {
            mainContainer.showNewsFeed(this);
        } else if (viewState == ViewState.About) {
            AboutNavigator aboutNavigator = mainContainer.showAboutView(this);
            setChildNavigator(aboutNavigator);
        } else if (viewState == ViewState.Team) {
            mainContainer.showTeam(this);
        } else {
            mainContainer.show(viewState);
        }
    }

    @Override
    public void goBackToViewState(ViewState viewState) {
        if (viewState == ViewState.RunDetails) {
            Integer reachId = reachIds.pop();
            mainContainer.showRunDetails(reachId, this);
        } else if (viewState == ViewState.GageDetails) {
            Gage gage = gages.pop();
            mainContainer.showGageDetails(gage, this);
        } else {
            showViewState(viewState);
        }
    }

    public void showAbout() {
        pushAndShowViewState(ViewState.About);
    }

    public void showTeam() {
        pushAndShowViewState(ViewState.Team);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////
    @Override public void onNewsClicked() {
        pushAndShowViewState(ViewState.News);
    }

    @Override public void onRunsClicked() {
        pushAndShowViewState(ViewState.RunsList);
    }

    @Override public void onFavoritesClicked() {
        pushAndShowViewState(ViewState.Favorites);
    }

    @Override public void onMapClicked() {
        pushAndShowViewState(ViewState.Map);
    }

    @Override public void onReachSelected(int reachId) {
        currentReachId = reachId;

        pushViewState(ViewState.RunDetails);
        RunDetailsNavigator runDetailsNavigator = mainContainer.showRunDetails(reachId, this);
        setChildNavigator(runDetailsNavigator);
    }

    @Override public void goToFilter() {
        pushAndShowViewState(ViewState.Filter);
    }

    @Override public void onGageSelected(Gage gage) {
        gages.push(gage);

        pushViewState(ViewState.GageDetails);
        mainContainer.showGageDetails(gage, this);
    }

    @Override public void onClose() {
        setChildNavigator(null);

        onBack();
    }

    @Override public void onArticleSelected(Article article) {
        pushViewState(ViewState.Article);
        mainContainer.showArticle(article, this);
    }

    @Override public void onReadMoreClicked() {
        pushAndShowViewState(ViewState.About);
    }
}
