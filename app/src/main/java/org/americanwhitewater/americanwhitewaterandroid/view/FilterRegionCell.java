package org.americanwhitewater.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.model.AWRegion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterRegionCell extends LinearLayout {
    @BindView(R.id.letter) TextView letterText;
    @BindView(R.id.title) TextView titleText;
    @BindView(R.id.checkbox) ImageView checkbox;

    public FilterRegionCell(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.cell_filter_region, this);
        onFinishInflate();
    }

    public FilterRegionCell(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.cell_filter_region, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    public void showLetter(String letter) {
        letterText.setText(letter);
        letterText.setVisibility(VISIBLE);
    }

    public void showRegion(AWRegion region) {
        titleText.setText(region.getTitle());
    }

    public void hideLetter() {
        letterText.setVisibility(INVISIBLE);
    }

    public void setCheckboxVisible(boolean isVisible) {
        if (isVisible) {
            checkbox.setVisibility(VISIBLE);
        } else {
            checkbox.setVisibility(GONE);
        }

    }
}
