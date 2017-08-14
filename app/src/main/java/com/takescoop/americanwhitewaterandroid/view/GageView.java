package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.squareup.picasso.Picasso;
import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.Gage;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;
import com.takescoop.americanwhitewaterandroid.model.api.Urls;
import com.takescoop.americanwhitewaterandroid.utility.AWIntent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

public class GageView extends ScrollView implements RunsAdapter.ItemClickListener {
    private static final String TAG = GageView.class.getSimpleName();
    private GageViewListener listener;

    private final AWApi awApi = AWProvider.Instance.awApi();
    private final Gage gage;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.gage_cell) GageCell gageCell;
    @BindView(R.id.flow_graph) ImageView flowGraph;
    @BindView(R.id.reach_list) RecyclerView reachList;

    public interface GageViewListener{
        void onReachSelected(int reachId);
        void onClose();
    }

    public GageView(Context context, Gage gage, GageViewListener listener) {
        super(context);

        this.gage = gage;
        this.listener = listener;

        LayoutInflater.from(context).inflate(R.layout.view_gage, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        displayGage(this.gage);
    }

    public void displayGage(Gage gage) {
        title.setText(gage.getName());
        gageCell.showGage(gage);

        String flowGraphUrl = awApi.getFlowGraphUrl(gage);
        if (!TextUtils.isEmpty(flowGraphUrl)) {
            Log.e(TAG, "displayGage " + flowGraphUrl);
            Picasso.with(getContext()).load(flowGraphUrl).into(flowGraph);
            flowGraph.setVisibility(VISIBLE);
        } else {
            flowGraph.setVisibility(GONE);
        }

        reachList.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ReachSearchResult> reaches = Lists.newArrayList();
        reachList.setAdapter(new RunsAdapter(getContext(), reaches, this));

        updateReaches(gage);
    }

    @OnClick(R.id.flow_graph)
    protected void onFlowGraphClick() {
        String url = String.format(Urls.GAGE_DETAIL_URL, gage.getId());
        AWIntent.goToUrl(getContext(), url);
    }

    @Override
    public void onReachItemClick(int reachId) {
        listener.onReachSelected(reachId);
    }

    @OnClick(R.id.back_tap_target)
    protected void onBackClick() {
        listener.onClose();
    }

    private void updateReaches(Gage gage) {
        awApi.getGageReaches(gage).subscribe(new DisposableSingleObserver<Gage>() {
            @Override public void onSuccess(@NonNull Gage gage) {
                RunsAdapter runsAdapter = (RunsAdapter) reachList.getAdapter();
                runsAdapter.setSearchResults(gage.getLinkedReaches());
                runsAdapter.notifyDataSetChanged();
            }

            @Override public void onError(@NonNull Throwable e) {

            }
        });
    }
}
