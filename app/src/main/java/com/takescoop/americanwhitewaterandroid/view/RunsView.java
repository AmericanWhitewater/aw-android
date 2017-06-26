package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.AWRegion;
import com.takescoop.americanwhitewaterandroid.model.FavoriteManager;
import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.model.FilterManager;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;
import com.takescoop.americanwhitewaterandroid.utility.DisplayStringUtils;

import org.threeten.bp.Instant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableSingleObserver;

public class RunsView extends RelativeLayout implements RunsAdapter.ItemClickListener {
    private static final String TAG = RunsView.class.getSimpleName();

    private final FilterManager filterManager = AWProvider.Instance.getFilterManager();
    private final FavoriteManager favoriteManager = AWProvider.Instance.getFavoriteManager();

    private RunsListener runsListener;

    @BindView(R.id.view_run_last_updated_text) TextView lastUpdatedText;
    @BindView(R.id.view_run_list) RecyclerView runList;
    @BindView(R.id.view_run_show_runnable) Switch showRunnableSwitch;

    public interface RunsListener {
        void onReachSelected(int reachId);
    }

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

        favoriteManager.retrieveFavorites();
        updateReaches(filterManager.getFilter());
    }

    @Override
    public void onReachItemClick(int reachId) {
        if (runsListener != null) {
            runsListener.onReachSelected(reachId);
        }
    }

    public void setRunsListener(RunsListener runsListener) {
        this.runsListener = runsListener;
    }

    private void updateReaches(Filter filter) {
        AWApi.Instance.getReaches(filter).subscribe(new DisposableSingleObserver<List<ReachSearchResult>>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull List<ReachSearchResult> reachSearchResults) {
                runList.setAdapter(new RunsAdapter(getContext(), reachSearchResults, RunsView.this));
                lastUpdatedText.setText(DisplayStringUtils.displayUpdateTime(Instant.now()));
            }

            @Override public void onError(@io.reactivex.annotations.NonNull Throwable e) {

            }
        });
    }
}
