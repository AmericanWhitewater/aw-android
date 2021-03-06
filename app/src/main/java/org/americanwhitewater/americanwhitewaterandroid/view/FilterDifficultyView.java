package org.americanwhitewater.americanwhitewaterandroid.view;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox ;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.common.collect.Lists;
import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.model.Difficulty;
import org.americanwhitewater.americanwhitewaterandroid.model.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterDifficultyView extends LinearLayout {
    private Filter filter;

    @BindView(R.id.class_1) AppCompatCheckBox class1;
    @BindView(R.id.class_2) AppCompatCheckBox class2;
    @BindView(R.id.class_3) AppCompatCheckBox class3;
    @BindView(R.id.class_4) AppCompatCheckBox class4;
    @BindView(R.id.class_5) AppCompatCheckBox class5;
    @BindView(R.id.class_5_plus) AppCompatCheckBox class5Plus;

    public FilterDifficultyView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_filter_difficulty, this);
        onFinishInflate();
    }

    public FilterDifficultyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_filter_difficulty, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    public Filter getFilter() {
        filter.setDifficultyLowerBound(getLowerBound());
        filter.setDifficultyUpperBound(getUpperBound());

        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;

        populate(filter);
    }

    @Nullable
    public Difficulty getLowerBound() {
        if (class1.isChecked()) {
            return Difficulty.I;
        } else if (class2.isChecked()) {
            return Difficulty.II;
        } else if (class3.isChecked()) {
            return Difficulty.III;
        } else if (class4.isChecked()) {
            return Difficulty.IV;
        } else if (class5.isChecked()) {
            return Difficulty.V;
        } else if (class5Plus.isChecked()) {
            return Difficulty.VPlus;
        }

        return null;
    }


    @Nullable
    public Difficulty getUpperBound() {
        if (class5Plus.isChecked()) {
            return Difficulty.VPlus;
        } else if (class5.isChecked()) {
            return Difficulty.V;
        } else if (class4.isChecked()) {
            return Difficulty.IV;
        } else if (class3.isChecked()) {
            return Difficulty.III;
        } else if (class2.isChecked()) {
            return Difficulty.II;
        } else if (class1.isChecked()) {
            return Difficulty.I;
        }

        return null;
    }

    private void populate(Filter filter) {
        if (filter.getDifficultyLowerBound() == null || filter.getDifficultyUpperBound() == null) {
            return;
        }

        List<AppCompatCheckBox> checkboxes = Lists.newArrayList(class1, class2, class3, class4, class5, class5Plus);
        for (AppCompatCheckBox checkBox : checkboxes) {
            Difficulty difficulty = difficultyForView(checkBox);
            boolean isChecked = difficulty.ordinal() >= filter.getDifficultyLowerBound().ordinal() && difficulty.ordinal() <= filter.getDifficultyUpperBound().ordinal();
            checkBox.setChecked(isChecked);
        }
    }

    private Difficulty difficultyForView(AppCompatCheckBox view) {
        if (view == class1) {
            return Difficulty.I;
        } else if (view == class2) {
            return Difficulty.II;
        } else if (view == class3) {
            return Difficulty.III;
        } else if (view == class4) {
            return Difficulty.IV;
        } else if (view == class5) {
            return Difficulty.V;
        } else if (view == class5Plus) {
            return Difficulty.VPlus;
        } else {
            throw new IllegalArgumentException("No associated difficulty");
        }
    }
}
