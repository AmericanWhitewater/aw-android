package org.americanwhitewater.americanwhitewaterandroid.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.model.Gage;
import org.americanwhitewater.americanwhitewaterandroid.model.ReachSearchResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InactiveCell extends LinearLayout {
    @BindView(R.id.cell_inactive_title) TextView  title;
    @BindView(R.id.cell_inactive_detail) TextView detail;
    @BindView(R.id.cell_inactive_length) TextView length;

    public InactiveCell(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.cell_inactive, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    public void showResult(ReachSearchResult result) {
        title.setText(result.getRiver());
        detail.setText(result.getName());

    }

    public void showGage(Gage gage) {
        title.setText(gage.getName());
        detail.setText(gage.getGageComment());

        length.setVisibility(GONE);
    }
}
