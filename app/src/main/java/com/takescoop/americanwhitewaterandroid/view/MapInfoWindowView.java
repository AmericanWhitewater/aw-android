package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.FlowLevel;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapInfoWindowView extends LinearLayout {
    @BindView(R.id.title) TextView title;
    @BindView(R.id.detail) TextView detail;
    @BindView(R.id.flow_difficulty) TextView flowDifficulty;

    public MapInfoWindowView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_map_info_window, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    public void display(ReachSearchResult result) {
        title.setText(result.getName());
        detail.setText(result.getRiver());

        String flowDifficultyString = String.format(getContext().getString(R.string.flow_and_difficulty),
                getFlowLevelString(result.getFlowLevel()), result.getDifficulty());

        flowDifficulty.setText(flowDifficultyString);
        flowDifficulty.setTextColor(ContextCompat.getColor(getContext(), result.getFlowLevel().getColorCode()));

        flowDifficulty.setVisibility(VISIBLE);
    }

    public void display(String titleString, String reachName) {
        title.setText(titleString);
        detail.setText(reachName);

        flowDifficulty.setVisibility(GONE);
    }

    private String getFlowLevelString(FlowLevel flowLevel) {
        String levelString = getContext().getString(R.string.no_info);

        switch (flowLevel) {
            case Low:
                levelString = getContext().getString(R.string.low);
                break;
            case Runnable:
                levelString = getContext().getString(R.string.runnable);
                break;
            case High:
                levelString = getContext().getString(R.string.high);
                break;
            case Frozen:
                levelString = getContext().getString(R.string.frozen);
                break;
            case NoInfo:
                levelString = getContext().getString(R.string.no_info);
                break;
        }

        return levelString;
    }
}