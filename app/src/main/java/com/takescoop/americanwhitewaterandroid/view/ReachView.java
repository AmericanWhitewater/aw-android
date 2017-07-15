package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.RunDetailsNavigator;
import com.takescoop.americanwhitewaterandroid.model.FavoriteManager;
import com.takescoop.americanwhitewaterandroid.model.Gage;
import com.takescoop.americanwhitewaterandroid.model.Reach;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

import static com.takescoop.americanwhitewaterandroid.controller.RunDetailsNavigator.ReachViewState.Details;
import static com.takescoop.americanwhitewaterandroid.controller.RunDetailsNavigator.ReachViewState.Map;
import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.DISABLED_ALPHA;
import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.ENABLED_ALPHA;

public class ReachView extends LinearLayout {
    private AWApi awApi = AWProvider.Instance.awApi();
    private final FavoriteManager favoriteManager = AWProvider.Instance.getFavoriteManager();

    private RunDetailsListener listener;
    private int reachId;


    @BindView(R.id.progressWheel) ProgressBar progressWheel;
    @BindView(R.id.back) ImageView back;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.favorite) ImageView favorite;
    @BindView(R.id.toolbar) LinearLayout toolbar;
    @BindView(R.id.details_tab) TextView detailsTab;
    @BindView(R.id.map_tab) TextView mapTab;
    @BindView(R.id.details_tab_highlight) View detailsTabHighlight;
    @BindView(R.id.map_tab_highlight) View mapTabHighlight;
    @BindView(R.id.detail) ReachDetailView detail;
    @BindView(R.id.map) ReachMapView map;

    public interface RunDetailsListener {
        void onDetailsClicked();

        void onMapClicked();

        void onGageSelected(Gage gage);

        void onClose();
    }

    public ReachView(Context context, RunDetailsListener runDetailsListener, int reachId) {
        super(context);

        this.listener = runDetailsListener;
        this.reachId = reachId;

        LayoutInflater.from(context).inflate(R.layout.view_reach, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        showFavorite(favoriteManager.isFavorite(reachId));
        fetchReach(reachId);
    }

    public void fetchReach(int reachId) {
        progressWheel.setVisibility(VISIBLE);
        awApi.getReach(reachId).subscribe(new DisposableSingleObserver<Reach>() {
            @Override public void onSuccess(@NonNull Reach reach) {
                progressWheel.setVisibility(GONE);
                updateReach(reach);
            }

            @Override public void onError(@NonNull Throwable e) {
                progressWheel.setVisibility(GONE);
            }
        });
    }

    private void updateReach(Reach reach) {
        title.setText(reach.getName());

        detail.showReach(reach);
        detail.setListener(gage -> {
            if (listener != null) {
                listener.onGageSelected(gage);
            }
        });
        map.showReach(reach);
    }

    @OnClick(R.id.details_tab)
    protected void onDetailsClicked() {
        listener.onDetailsClicked();
    }

    @OnClick(R.id.map_tab)
    protected void onMapClicked() {
        listener.onMapClicked();
    }

    @OnClick(R.id.back)
    protected void onBackClick() {
        listener.onClose();
    }

    @OnClick(R.id.favorite)
    protected void onFavoriteClick() {
        boolean isFavorite = favoriteManager.toggleFavorite(reachId);
        showFavorite(isFavorite);
    }

    public void showViewState(RunDetailsNavigator.ReachViewState viewState) {
        updateTabUI(viewState);

        switch (viewState) {
            case Details:
                detail.setVisibility(VISIBLE);
                map.setVisibility(INVISIBLE);
                break;
            case Map:
                detail.setVisibility(INVISIBLE);
                map.setVisibility(VISIBLE);
                break;
        }
    }

    private void showFavorite(boolean isFavorite) {
        if (isFavorite) {
            favorite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fav_yes));

        } else {
            favorite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fav_no));
        }
    }

    private void updateTabUI(RunDetailsNavigator.ReachViewState viewState) {
        setHighlighted(viewState == Details, detailsTab, detailsTabHighlight);
        setHighlighted(viewState == Map, mapTab, mapTabHighlight);
    }

    private void setHighlighted(boolean isHighlighted, TextView tabText, View tabHighlight) {
        if (isHighlighted) {
            tabText.setAlpha(ENABLED_ALPHA);
            tabHighlight.setVisibility(VISIBLE);

        } else {
            tabText.setAlpha(DISABLED_ALPHA);
            tabHighlight.setVisibility(INVISIBLE);
        }
    }
}
