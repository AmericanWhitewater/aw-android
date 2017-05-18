package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.Gage;
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

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    public void showGage(Gage gage) {
        title.setText(gage.getName());
        detail.setText(gage.getGageComment());
        updateTime.setText(DisplayStringUtils.getGageDisplay(gage.getLastUpdated()));
        levelNumber.setText(gage.getCurrentLevel());
        unit.setText(gage.getUnit());
        levelDescription.setText(gage.getGageComment());
    }
}
