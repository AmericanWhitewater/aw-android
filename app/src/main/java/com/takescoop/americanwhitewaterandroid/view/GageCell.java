package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.Gage;
import com.takescoop.americanwhitewaterandroid.model.Reach;
import com.takescoop.americanwhitewaterandroid.utility.DisplayStringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GageCell extends LinearLayout {

    @BindView(R.id.cell_gage_title) TextView title;
    @BindView(R.id.cell_gage_detail) TextView detail;
    @BindView(R.id.cell_gage_update_time) TextView updateTime;
    @BindView(R.id.cell_gage_level_number) TextView levelNumber;
    @BindView(R.id.cell_gage_unit) TextView unit;
    @BindView(R.id.cell_gage_level_text) TextView levelDescription;

    public GageCell(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.cell_gage, this);
        onFinishInflate();
    }

    public GageCell(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.cell_gage, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    public void showReach(@Nullable Reach reach) {
        if (reach == null || reach.getGage() == null) {
            this.setVisibility(GONE);
            return;
        }

        Gage gage = reach.getGage();

        title.setText(reach.getRiver());
        detail.setText(reach.getName());
        updateTime.setText(DisplayStringUtils.displayUpdateTime(gage.getLastUpdated()));
        levelNumber.setText(gage.getCurrentLevel());
        unit.setText(gage.getUnit());
//        TODO levelDescription.setText(gage.getGageComment());

        this.setVisibility(VISIBLE);
    }

    public void showGage(@Nullable Gage gage) {
        if (gage == null) {
            return;
        }

        title.setText(gage.getName());
        if (TextUtils.isEmpty(gage.getGageComment())) {
            detail.setVisibility(GONE);
        } else {
            detail.setText(gage.getGageComment());
            detail.setVisibility(VISIBLE);
        }
        updateTime.setText(DisplayStringUtils.displayUpdateTime(gage.getLastUpdated()));
        levelNumber.setText(gage.getCurrentLevel());
        unit.setText(gage.getUnit());
//        levelDescription.setText(gage.getGageComment());
    }
}
