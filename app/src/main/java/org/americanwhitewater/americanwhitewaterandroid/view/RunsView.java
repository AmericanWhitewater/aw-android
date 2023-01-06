package org.americanwhitewater.americanwhitewaterandroid.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.Lists;
import org.americanwhitewater.americanwhitewaterandroid.AWProvider;
import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.controller.LocationProviderActivity;
import org.americanwhitewater.americanwhitewaterandroid.model.AWRegion;
import org.americanwhitewater.americanwhitewaterandroid.model.FavoriteManager;
import org.americanwhitewater.americanwhitewaterandroid.model.Filter;
import org.americanwhitewater.americanwhitewaterandroid.model.FilterManager;
import org.americanwhitewater.americanwhitewaterandroid.model.ReachSearchResult;
import org.americanwhitewater.americanwhitewaterandroid.model.api.AWApi;
import org.americanwhitewater.americanwhitewaterandroid.utility.Dialogs;
import org.americanwhitewater.americanwhitewaterandroid.utility.DisplayStringUtils;
import org.americanwhitewater.americanwhitewaterandroid.utility.Listener;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.SingleSubject;

public class RunsView extends RelativeLayout implements RunsAdapter.ItemClickListener {
    private static final int DEFAULT_DISTANCE_RADIUS = 70; // Should be even
    private static final int LOCATION_TIMEOUT_s = 4;

    private final AWApi awApi = AWProvider.Instance.awApi();
    private final FilterManager filterManager = AWProvider.Instance.getFilterManager();
    private final FavoriteManager favoriteManager = AWProvider.Instance.getFavoriteManager();

    private RunsListener runsListener;

    @BindView(R.id.view_run_last_updated_text) TextView lastUpdatedText;
    @BindView(R.id.view_run_no_results_text) TextView noResultsText;
    @BindView(R.id.view_run_go_to_filter) TextView viewRunGoToFilter;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.view_run_list) RecyclerView runList;
    @BindView(R.id.view_run_show_runnable) Switch showRunnableSwitch;
    @BindView(R.id.view_run_runnable_layout) LinearLayout viewRunRunnableLayout;

    public interface RunsListener {
        void onReachSelected(int reachId);

        void goToFilter();
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

        init();
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

    @OnClick(R.id.view_run_runnable_layout)
    protected void onRunnableLayoutClick() {
        // Swallow
    }

    @OnClick(R.id.view_run_go_to_filter)
    protected void onGoToFilterClick() {
        if (runsListener != null) {
            runsListener.goToFilter();
        }
    }

    private void init() {
        runList.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeContainer.setOnRefreshListener(() -> updateReaches(filterManager.getFilter()));
        swipeContainer.setColorSchemeResources(R.color.primary);

        // Necessary because otherwise the swipe container won't show up
        getRunsAdapter().setSearchResults(Lists.newArrayList());

        showRunnableSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            RunsAdapter adapter = getRunsAdapter();
            adapter.setShowRunnableOnly(isChecked);
            adapter.notifyDataSetChanged();
        });

        favoriteManager.retrieveFavorites();

        // If no region or distance filter is set, show the default.
        if (!filterManager.getFilter().hasRegion() && !filterManager.getFilter().hasRadius()) {
            filterManager.getFilter().setRadius(DEFAULT_DISTANCE_RADIUS);
            filterManager.save();
        }

        if (getContext() instanceof LocationProviderActivity && filterManager.getFilter().getCurrentLocation() == null) {
            swipeContainer.setRefreshing(true);
            getCurrentLocation(new DisposableSingleObserver<LatLng>() {
                @Override public void onSuccess(@NonNull LatLng latLng) {
                    filterManager.getFilter().setCurrentLocation(latLng);
                    filterManager.save();
                    updateReaches(filterManager.getFilter());
                }

                @Override public void onError(@NonNull Throwable e) {
                    updateReaches(filterManager.getFilter());
                }
            });
        } else {
            updateReaches(filterManager.getFilter());
        }
    }

    private void updateReaches(Filter filter) {
        swipeContainer.setRefreshing(true);
        awApi.getReaches(filter).subscribe(new DisposableSingleObserver<List<ReachSearchResult>>() {
            @Override
            public void onSuccess(@NonNull List<ReachSearchResult> reachSearchResults) {
                if (reachSearchResults.isEmpty()) {
                    noResultsText.setVisibility(VISIBLE);
                    viewRunGoToFilter.setVisibility(VISIBLE);
                } else {
                    noResultsText.setVisibility(GONE);
                    viewRunGoToFilter.setVisibility(GONE);
                }

                getRunsAdapter().setSearchResults(reachSearchResults);
                getRunsAdapter().setShowRunnableOnly(showRunnableSwitch.isChecked());
                getRunsAdapter().notifyDataSetChanged();

                lastUpdatedText.setText(DisplayStringUtils.displayUpdateTime(Instant.now()));

                swipeContainer.setRefreshing(false);
            }

            @Override public void onError(@NonNull Throwable e) {
                swipeContainer.setRefreshing(false);

                Dialogs.toast("Could not retrieve reaches");
            }
        });
    }

    private RunsAdapter getRunsAdapter() {
        RunsAdapter adapter = (RunsAdapter) runList.getAdapter();
        if (adapter == null) {
            adapter = new RunsAdapter(getContext(), Lists.newArrayList(), RunsView.this);
            runList.setAdapter(adapter);
        }

        return adapter;
    }

    private void getCurrentLocation(DisposableSingleObserver<LatLng> locationObservable) {
        LocationProviderActivity locationActivity = (LocationProviderActivity) getContext();
        SingleSubject<LatLng> locationSubject = SingleSubject.create();
        locationSubject.timeout(LOCATION_TIMEOUT_s, TimeUnit.SECONDS).subscribe(locationObservable);

        locationActivity.getCurrentLocation(locationSubject);
    }

    private void updateCurrentRegion(LatLng latLng, Listener<AWRegion> listener) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // 1 is the max addresses returned

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                listener.onResponse(AWRegion.fromStateName(address.getAdminArea()));
            }

        } catch (IOException e) {
            listener.onResponse(null);
        }
    }
}
