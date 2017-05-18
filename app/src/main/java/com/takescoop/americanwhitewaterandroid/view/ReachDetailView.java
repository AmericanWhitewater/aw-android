package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.Reach;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReachDetailView extends LinearLayout {
    private static final int DESCRIPTION_MAX_LINES = 4;

    @BindView(R.id.gage_cell) GageCell gageCell;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.read_more) TextView readMore;
    @BindView(R.id.difficulty) TextView difficulty;
    @BindView(R.id.length) TextView length;
    @BindView(R.id.gradient) TextView gradient;

    public ReachDetailView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_reach_details, this);
    }

    public ReachDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_reach_details, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        description.setMaxLines(DESCRIPTION_MAX_LINES);
    }

    public void showReach(Reach reach) {
        gageCell.showGage(reach.getGage());
        description.setText(reach.getDescription());
        difficulty.setText(reach.getDifficulty());
        length.setText(reach.getLength());
        gradient.setText(reach.getAvgGradient());
    }

    @OnClick(R.id.description_layout)
    protected void onDescriptionLayoutClick() {
        if (description.getMaxLines() == DESCRIPTION_MAX_LINES) {
            description.setMaxLines(Integer.MAX_VALUE);
        } else {
            description.setMaxLines(DESCRIPTION_MAX_LINES);
        }
    }

    @OnClick(R.id.gage_cell)
    protected void onGageClick() {

    }
}
