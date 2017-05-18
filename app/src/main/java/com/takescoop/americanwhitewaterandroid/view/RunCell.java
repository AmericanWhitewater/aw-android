package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.FlowLevel;
import com.takescoop.americanwhitewaterandroid.model.Reach;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RunCell extends LinearLayout {
    @BindView(R.id.cell_run_highlight) View highlight;
    @BindView(R.id.cell_run_title) TextView title;
    @BindView(R.id.cell_run_detail) TextView detail;
    @BindView(R.id.cell_run_level) TextView level;
    @BindView(R.id.cell_run_length) TextView length;
    @BindView(R.id.cell_run_favorite) ImageView favorite;

    public RunCell(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.cell_run, this);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    public void showResult(ReachSearchResult result) {
        showFlowLevel(result.getFlowLevel());

        String levelText = String.format(getContext().getString(R.string.level_class),
                result.getLastGaugeReading(), result.getDifficulty());
        level.setText(levelText);
        title.setText(result.getRiver());
        detail.setText(result.getName());

        //TODO favorite
        favorite.setColorFilter(ContextCompat.getColor(getContext(), R.color.font_grey));

        showActive();
    }

    public void showReach(Reach reach) {

        showActive();
    }

    public void showInactive() {
        highlight.setVisibility(GONE);
        level.setVisibility(GONE);
        favorite.setVisibility(GONE);

        title.setTextColor(ContextCompat.getColor(getContext(), R.color.font_grey));

        this.setAlpha(.38f);
    }

    private void showActive() {
        highlight.setVisibility(VISIBLE);
        level.setVisibility(VISIBLE);
        favorite.setVisibility(VISIBLE);

        title.setTextColor(ContextCompat.getColor(getContext(), R.color.font_black));

        this.setAlpha(1.0f);
    }

    private void showFlowLevel(FlowLevel flowLevel) {
        int colorId = flowLevel.getColorCode();
        highlight.setBackgroundColor(ContextCompat.getColor(getContext(), colorId));
        level.setTextColor(ContextCompat.getColor(getContext(), colorId));
    }
}