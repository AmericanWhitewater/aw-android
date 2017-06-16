package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.Gage;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GageView extends LinearLayout implements RunsAdapter.ItemClickListener {
    private final Gage gage;

    @BindView(R.id.gage_cell) GageCell gageCell;
    @BindView(R.id.flow_graph) ImageView flowGraph;
    @BindView(R.id.reach_list) RecyclerView reachList;

    public GageView(Context context, Gage gage) {
        super(context);

        this.gage = gage;

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
        gageCell.showGage(gage);
        // TODO flowGraph

        reachList.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ReachSearchResult> reaches = Lists.newArrayList();
        reachList.setAdapter(new RunsAdapter(getContext(), reaches, this));
    }

    @Override
    public void onReachItemClick(int reachId) {

    }
}
