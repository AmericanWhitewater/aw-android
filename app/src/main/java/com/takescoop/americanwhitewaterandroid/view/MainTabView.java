package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.takescoop.americanwhitewaterandroid.view.MainTabView.TabViewState.Favorites;
import static com.takescoop.americanwhitewaterandroid.view.MainTabView.TabViewState.Map;
import static com.takescoop.americanwhitewaterandroid.view.MainTabView.TabViewState.News;
import static com.takescoop.americanwhitewaterandroid.view.MainTabView.TabViewState.Runs;
import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.DISABLED_ALPHA;
import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.ENABLED_ALPHA;

public class MainTabView extends LinearLayout {

    // Initialized empty
    private TabListener tabListener = emptyListener();
    private TabViewState currentViewState;
    
    @BindView(R.id.news_tab) LinearLayout newsTab;
    @BindView(R.id.runs_tab) LinearLayout runsTab;
    @BindView(R.id.favorites_tab) LinearLayout favoritesTab;
    @BindView(R.id.map_tab) LinearLayout mapTab;

    public enum  TabViewState {
        News, Runs, Favorites, Map;
    }

    public interface TabListener {
        void onNewsClicked();
        void onRunsClicked();
        void onFavoritesClicked();
        void onMapClicked();
    }
    
    public MainTabView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_main_tabs, this);
        onFinishInflate();
    }

    public MainTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_main_tabs, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        // Default to runs
        setViewState(Runs);
    }

    public void setTabListener(@NonNull TabListener tabListener) {
        this.tabListener = tabListener;
    }

    public void setViewState(TabViewState tabViewState) {
        currentViewState = tabViewState;

        if (tabViewState == News) {
            newsTab.setAlpha(ENABLED_ALPHA);
        } else {
            newsTab.setAlpha(DISABLED_ALPHA);
        }

        if (tabViewState == Runs) {
            runsTab.setAlpha(ENABLED_ALPHA);
        } else {
            runsTab.setAlpha(DISABLED_ALPHA);
        }

        if (tabViewState == Favorites) {
            favoritesTab.setAlpha(ENABLED_ALPHA);
        } else {
            favoritesTab.setAlpha(DISABLED_ALPHA);
        }

        if (tabViewState == Map) {
            mapTab.setAlpha(ENABLED_ALPHA);
        } else {
            mapTab.setAlpha(DISABLED_ALPHA);
        }
    }


    @OnClick(R.id.news_tab)
    protected void onNewsClicked() {
        if (currentViewState == News) {
            return;
        }

        tabListener.onNewsClicked();
        setViewState(News);
    }

    @OnClick(R.id.runs_tab)
    protected void onRunsClicked() {
        if (currentViewState == Runs) {
            return;
        }

        tabListener.onRunsClicked();
        setViewState(Runs);
    }

    @OnClick(R.id.favorites_tab)
    protected void onFavoritesClicked() {
        if (currentViewState == Favorites) {
            return;
        }

        tabListener.onFavoritesClicked();
        setViewState(Favorites);
    }

    @OnClick(R.id.map_tab)
    protected void onMapClicked() {
        if (currentViewState == Map) {
            return;
        }

        tabListener.onMapClicked();
        setViewState(Map);
    }
    
    private TabListener emptyListener() {
        return new TabListener() {
            @Override public void onNewsClicked() {
                
            }

            @Override public void onRunsClicked() {

            }

            @Override public void onFavoritesClicked() {

            }

            @Override public void onMapClicked() {

            }
        };
    }
}
