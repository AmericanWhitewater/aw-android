package org.americanwhitewater.americanwhitewaterandroid.view;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.americanwhitewater.americanwhitewaterandroid.AWProvider;
import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.model.FavoriteManager;
import org.americanwhitewater.americanwhitewaterandroid.model.ReachSearchResult;
import org.americanwhitewater.americanwhitewaterandroid.model.api.AWApi;
import org.americanwhitewater.americanwhitewaterandroid.utility.DisplayStringUtils;

import org.threeten.bp.Instant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

public class FavoritesView extends LinearLayout implements RunsAdapter.ItemClickListener {
    private final AWApi awApi = AWProvider.Instance.awApi();
    private final FavoriteManager favoriteManager = AWProvider.Instance.getFavoriteManager();
    private RunsView.RunsListener runsListener;

    @BindView(R.id.no_favorites_layout) LinearLayout noFavoritesLayout;
    @BindView(R.id.last_updated_text) TextView lastUpdatedText;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.favorites_list) RecyclerView favoritesList;

    public FavoritesView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_favorites, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        init();
    }

    public void setRunsListener(RunsView.RunsListener runsListener) {
        this.runsListener = runsListener;
    }

    private void init() {
        favoritesList.setLayoutManager(new LinearLayoutManager(getContext()));

        favoriteManager.retrieveFavorites();
        updateFavorites();

        swipeContainer.setOnRefreshListener(this::updateFavorites);
        swipeContainer.setColorSchemeResources(R.color.primary);
    }

    private void updateFavorites() {
        if (!favoriteManager.hasFavorite()) {
            noFavoritesLayout.setVisibility(VISIBLE);
            lastUpdatedText.setVisibility(GONE);
            favoritesList.setVisibility(GONE);
            return;
        }

        noFavoritesLayout.setVisibility(GONE);
        lastUpdatedText.setVisibility(VISIBLE);
        favoritesList.setVisibility(VISIBLE);

        swipeContainer.setRefreshing(true);
        awApi.getReaches(favoriteManager.getFavoriteReachIds()).subscribe(new DisposableSingleObserver<List<ReachSearchResult>>() {
            @Override public void onSuccess(@NonNull List<ReachSearchResult> results) {
                favoritesList.setAdapter(new RunsAdapter(getContext(), results, FavoritesView.this));
                lastUpdatedText.setText(DisplayStringUtils.displayUpdateTime(Instant.now()));

                swipeContainer.setRefreshing(false);
            }

            @Override public void onError(@NonNull Throwable e) {
                swipeContainer.setRefreshing(false);
            }
        });
    }

    @Override public void onReachItemClick(int reachId) {
        if (runsListener != null) {
            runsListener.onReachSelected(reachId);
        }
    }
}
