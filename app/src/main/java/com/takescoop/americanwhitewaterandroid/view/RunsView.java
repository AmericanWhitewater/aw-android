package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.controller.LocationProviderActivity;
import com.takescoop.americanwhitewaterandroid.model.AWRegion;
import com.takescoop.americanwhitewaterandroid.model.FavoriteManager;
import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.model.FilterManager;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;
import com.takescoop.americanwhitewaterandroid.utility.Dialogs;
import com.takescoop.americanwhitewaterandroid.utility.DisplayStringUtils;
import com.takescoop.americanwhitewaterandroid.utility.Listener;

import org.threeten.bp.Instant;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.SingleSubject;

public class RunsView extends RelativeLayout implements RunsAdapter.ItemClickListener {
    private static final String TAG = RunsView.class.getSimpleName();

    private final AWApi awApi = AWProvider.Instance.awApi();
    private final FilterManager filterManager = AWProvider.Instance.getFilterManager();
    private final FavoriteManager favoriteManager = AWProvider.Instance.getFavoriteManager();
    @BindView(R.id.view_run_runnable_layout) LinearLayout viewRunRunnableLayout;

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

        // Retrieve current location if there is no region
        if (getContext() instanceof LocationProviderActivity && !filterManager.getFilter().hasRegion()) {
            updateCurrentRegion(region -> {
                filterManager.getFilter().addRegion(region);
                filterManager.save();
                updateReaches(filterManager.getFilter());
            });
        } else {
            updateReaches(filterManager.getFilter());
        }
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

    private void updateReaches(Filter filter) {
        awApi.getReaches(filter).subscribe(new DisposableSingleObserver<List<ReachSearchResult>>() {
            @Override
            public void onSuccess(@NonNull List<ReachSearchResult> reachSearchResults) {
                runList.setAdapter(new RunsAdapter(getContext(), reachSearchResults, RunsView.this));
                lastUpdatedText.setText(DisplayStringUtils.displayUpdateTime(Instant.now()));
            }

            @Override public void onError(@NonNull Throwable e) {

            }
        });
    }

    private void updateCurrentRegion(Listener<AWRegion> listener) {
        LocationProviderActivity locationActivity = (LocationProviderActivity) getContext();
        SingleSubject<LatLng> locationObservable = SingleSubject.create();
        locationObservable.subscribe(new DisposableSingleObserver<LatLng>() {
            @Override public void onSuccess(@NonNull LatLng latLng) {

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

            @Override public void onError(@NonNull Throwable e) {
                Dialogs.toast(e.getMessage());
            }
        });

        locationActivity.getCurrentLocation(locationObservable);
    }
}
