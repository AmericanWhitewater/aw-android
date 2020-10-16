package org.americanwhitewater.americanwhitewaterandroid.view;

import android.content.Context;
import androidx.appcompat.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.americanwhitewater.americanwhitewaterandroid.AWProvider;
import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.controller.RunDetailsNavigator;
import org.americanwhitewater.americanwhitewaterandroid.model.FavoriteManager;
import org.americanwhitewater.americanwhitewaterandroid.model.Gage;
import org.americanwhitewater.americanwhitewaterandroid.model.Reach;
import org.americanwhitewater.americanwhitewaterandroid.model.api.AWApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

public class ReachView extends LinearLayout {
    private AWApi awApi = AWProvider.Instance.awApi();
    private final FavoriteManager favoriteManager = AWProvider.Instance.getFavoriteManager();

    private RunDetailsListener listener;
    private int reachId;


    @BindView(R.id.progressWheel) ProgressBar progressWheel;
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

    @OnClick(R.id.back_tap_target)
    protected void onBackClick() {
        listener.onClose();
    }

    @OnClick(R.id.favorite_tap_target)
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
            favorite.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_fav_yes));

        } else {
            favorite.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_fav_no));
        }
    }

    private void updateTabUI(RunDetailsNavigator.ReachViewState viewState) {
        setHighlighted(viewState == RunDetailsNavigator.ReachViewState.Details, detailsTab, detailsTabHighlight);
        setHighlighted(viewState == RunDetailsNavigator.ReachViewState.Map, mapTab, mapTabHighlight);
    }

    private void setHighlighted(boolean isHighlighted, TextView tabText, View tabHighlight) {
        if (isHighlighted) {
            tabText.setAlpha(ViewConstants.ENABLED_ALPHA);
            tabHighlight.setVisibility(VISIBLE);

        } else {
            tabText.setAlpha(ViewConstants.DISABLED_ALPHA);
            tabHighlight.setVisibility(INVISIBLE);
        }
    }
}
