package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.AWRegion;
import com.takescoop.americanwhitewaterandroid.model.FilterManager;
import com.takescoop.americanwhitewaterandroid.utility.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterRegionView extends LinearLayout implements Listener<AWRegion> {
    private final FilterManager filterManager = AWProvider.Instance.getFilterManager();

    @BindView(R.id.fr_current_location) TextView currentLocationText;
    @BindView(R.id.fr_list) RecyclerView list;

    public FilterRegionView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_filter_region, this);
        onFinishInflate();
    }

    public FilterRegionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_filter_region, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        Set<AWRegion> selectedRegions = Sets.newHashSet(filterManager.getFilter().getRegions());
        displaySelectedRegions(filterManager.getFilter().getRegions());
        list.setAdapter(new FilterRegionAdapter(getContext(), selectedRegions, this));
    }

    public List<AWRegion> getSelectedRegions() {
        FilterRegionAdapter adapter = (FilterRegionAdapter) list.getAdapter();
        return Lists.newArrayList(adapter.getSelections());
    }

    @Override
    public void onResponse(AWRegion region) {
        displaySelectedRegions(getSelectedRegions());
    }

    private void displaySelectedRegions(List<AWRegion> selectedRegions) {
        currentLocationText.setText(TextUtils.join(", ", selectedRegions));
    }

    public class FilterRegionAdapter extends RecyclerView.Adapter<FilterRegionAdapter.FilterRegionCellViewHolder> {
        private Context context;
        private Set<AWRegion> selections;
        private Listener<AWRegion> listener;
        private List<AWRegion> regions = Lists.newArrayList(AWRegion.values());

        public FilterRegionAdapter(Context context, Set<AWRegion> selections, Listener<AWRegion> listener) {
            this.context = context;
            this.listener = listener;
            this.selections = selections;
        }

        @Override
        public FilterRegionCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            FilterRegionCell filterCell = new FilterRegionCell(context);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            filterCell.setLayoutParams(lp);

            return new FilterRegionCellViewHolder(filterCell);
        }

        @Override
        public void onBindViewHolder(FilterRegionCellViewHolder holder, int position) {
            AWRegion region = regions.get(position);
            holder.getFilterCell().showRegion(region);

            boolean hasPrevious = position > 0;
            if (!hasPrevious || hasDifferentFirstLetter(region, regions.get(position - 1))) {
                holder.getFilterCell().showLetter(region.getTitle().substring(0, 1));
            } else {
                holder.getFilterCell().hideLetter();
            }

            holder.getFilterCell().setCheckboxVisible(isSelected(region));
            holder.getFilterCell().setOnClickListener(v -> {
                if (isSelected(region)) {
                    selections.remove(region);
                } else {
                    selections.add(region);
                }

                holder.getFilterCell().setCheckboxVisible(isSelected(region));

                listener.onResponse(region);
            });
        }

        @Override
        public int getItemCount() {
            return regions.size();
        }

        public Set<AWRegion> getSelections() {
            return selections;
        }

        private boolean hasDifferentFirstLetter(AWRegion region1, AWRegion region2) {
            return region1.getTitle().charAt(0) != region2.getTitle().charAt(0);
        }

        private boolean isSelected(AWRegion region) {
            return selections.contains(region);
        }

        class FilterRegionCellViewHolder extends RecyclerView.ViewHolder {
            private FilterRegionCell filterCell;

            public FilterRegionCellViewHolder(FilterRegionCell filterCell) {
                super(filterCell);

                this.filterCell = filterCell;
            }

            public FilterRegionCell getFilterCell() {
                return filterCell;
            }
        }

    }
}
