package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;

import java.util.List;

public class RunsAdapter extends RecyclerView.Adapter<RunsAdapter.RunCellViewHolder> {
    private Context context;
    private List<ReachSearchResult> searchResults;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onReachItemClick(int reachId);
    }

    public RunsAdapter(Context context, @NonNull List<ReachSearchResult> searchResults, ItemClickListener itemClickListener) {
        this.context = context;
        this.searchResults = searchResults;
        this.itemClickListener = itemClickListener;
    }

    public void setSearchResults(List<ReachSearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public RunsAdapter.RunCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RunCell runCell = new RunCell(context);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        runCell.setLayoutParams(lp);

        return new RunsAdapter.RunCellViewHolder(runCell);
    }

    @Override
    public void onBindViewHolder(RunsAdapter.RunCellViewHolder holder, int position) {
        ReachSearchResult result = searchResults.get(position);
        holder.getRunCell().showResult(result);

        holder.getRunCell().setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onReachItemClick(result.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    class RunCellViewHolder extends RecyclerView.ViewHolder {
        private RunCell runCell;

        public RunCellViewHolder(RunCell runCell) {
            super(runCell);

            this.runCell = runCell;
        }

        public RunCell getRunCell() {
            return runCell;
        }
    }
}
