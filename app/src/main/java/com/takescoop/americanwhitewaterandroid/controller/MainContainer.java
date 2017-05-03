package com.takescoop.americanwhitewaterandroid.controller;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.widget.Toolbar;

import com.takescoop.americanwhitewaterandroid.view.MainView;
import com.takescoop.americanwhitewaterandroid.view.MapView;
import com.takescoop.americanwhitewaterandroid.view.NewsFeedView;
import com.takescoop.americanwhitewaterandroid.view.ReachView;
import com.takescoop.americanwhitewaterandroid.view.RunsView;
import com.takescoop.americanwhitewaterandroid.view.SearchView;

public class MainContainer {
    private Context context;
    private ActionBar actionBar;
    private MainView mainView;

    public MainContainer(Context context, ActionBar actionBar, MainView mainView) {
        this.context = context;
        this.actionBar = actionBar;
        this.mainView = mainView;
    }

    public void show(MainNavigator.ViewState viewState) {
        switch (viewState) {
            case News:
                mainView.getTabContainer().removeAllViews();
                mainView.getTabContainer().addView(new NewsFeedView(context));

                mainView.hideModal();
                break;

            case Runs:
                mainView.getTabContainer().removeAllViews();
                mainView.getTabContainer().addView(new RunsView(context));

                mainView.hideModal();
                break;

            case Favorites:
                break;

            case Map:
                mainView.getTabContainer().removeAllViews();
                mainView.getTabContainer().addView(new MapView(context));

                mainView.hideModal();
                break;

            case Filter:
                mainView.getTabContainer().removeAllViews();
                mainView.getTabContainer().addView(new FilterVC(context));

                mainView.showModal();
                break;

            case Search:
                mainView.getTabContainer().removeAllViews();
                mainView.getTabContainer().addView(new SearchView(context));

                mainView.showModal();
                break;

            case Gage:
                mainView.getTabContainer().removeAllViews();
                mainView.getTabContainer().addView(new SearchView(context));

                mainView.hideModal();
                break;

            case RunDetails:
                mainView.getTabContainer().removeAllViews();
                mainView.getTabContainer().addView(new ReachView(context));

                mainView.hideModal();
                break;
        }
    }
}
