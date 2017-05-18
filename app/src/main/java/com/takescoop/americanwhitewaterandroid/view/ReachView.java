package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.FilterVC;
import com.takescoop.americanwhitewaterandroid.model.Reach;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.DISABLED_ALPHA;
import static com.takescoop.americanwhitewaterandroid.view.ViewConstants.ENABLED_ALPHA;

public class ReachView extends LinearLayout {
    private AWApi awApi = AWProvider.Instance.awApi();

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

    private enum ReachViewState {
        Details, Map;
    }

    public ReachView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_reach, this);
        onFinishInflate();
    }

    public ReachView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_reach, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        showViewState(ReachViewState.Details);
        fetchReach(3609);
    }

    private void fetchReach(int reachId) {
        awApi.getReach(reachId).subscribe(new DisposableSingleObserver<Reach>() {
            @Override public void onSuccess(@NonNull Reach reach) {
                updateReach(reach);
            }

            @Override public void onError(@NonNull Throwable e) {

            }
        });
    }

    private void updateReach(Reach reach) {
        detail.showReach(reach);
        map.showReach(reach);
    }

    private void showViewState(ReachViewState viewState) {
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

    private void updateTabUI(ReachViewState viewState) {
        setHighlighted(viewState == ReachViewState.Details, detailsTab, detailsTabHighlight);
        setHighlighted(viewState == ReachViewState.Map, mapTab, mapTabHighlight);
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
