package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.FavoriteManager;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;
import com.takescoop.americanwhitewaterandroid.utility.DisplayStringUtils;

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

        awApi.getReaches(favoriteManager.getFavoriteReachIds()).subscribe(new DisposableSingleObserver<List<ReachSearchResult>>() {
            @Override public void onSuccess(@NonNull List<ReachSearchResult> results) {
                favoritesList.setAdapter(new RunsAdapter(getContext(), results, FavoritesView.this));
                lastUpdatedText.setText(DisplayStringUtils.displayUpdateTime(Instant.now()));
            }

            @Override public void onError(@NonNull Throwable e) {

            }
        });
    }

    @Override public void onReachItemClick(int reachId) {
        if (runsListener != null) {
            runsListener.onReachSelected(reachId);
        }
    }
}
