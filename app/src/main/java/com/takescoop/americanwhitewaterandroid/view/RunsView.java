package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.AWRegion;
import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableSingleObserver;

public class RunsView extends RelativeLayout {
    private static final String TAG = RunsView.class.getSimpleName();

    @BindView(R.id.view_run_last_updated_text) TextView lastUpdatedText;
    @BindView(R.id.view_run_list) RecyclerView runList;
    @BindView(R.id.view_run_show_runnable) Switch showRunnableSwitch;

    public RunsView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_runs, this);
        onFinishInflate();
    }

    public RunsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_runs, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
        runList.setLayoutManager(new LinearLayoutManager(getContext()));

        showRunnableSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Log.e(TAG, "onCheckedChanged isChecked");
            }
        });

        Filter filter = new Filter();
        filter.addRegion(AWRegion.Kansas);
        AWApi.Instance.getReaches(filter).subscribe(new DisposableSingleObserver<List<ReachSearchResult>>() {
            @Override public void onSuccess(@io.reactivex.annotations.NonNull List<ReachSearchResult> reachSearchResults) {
                runList.setAdapter(new RunsAdapter(getContext(), reachSearchResults));
            }

            @Override public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.e(TAG, "onError " + e);
            }
        });

    }

    public class RunsAdapter extends RecyclerView.Adapter<RunsAdapter.RunCellViewHolder> {
        private Context context;
        private List<ReachSearchResult> searchResults;


        public RunsAdapter(Context context, @NonNull List<ReachSearchResult> searchResults) {
            this.context = context;
            this.searchResults = searchResults;
        }

        @Override
        public RunCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RunCell runCell = new RunCell(context);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            runCell.setLayoutParams(lp);

            return new RunCellViewHolder(runCell);
        }

        @Override
        public void onBindViewHolder(RunCellViewHolder holder, int position) {
            ReachSearchResult result = searchResults.get(position);
            holder.getRunCell().showResult(result);
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
}
