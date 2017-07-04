package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

public class SearchView extends LinearLayout implements RunsAdapter.ItemClickListener {
    AWApi awApi = AWProvider.Instance.awApi();
    private SearchListener listener;

    @BindView(R.id.back) ImageView back;
    @BindView(R.id.clear) ImageView clear;
    @BindView(R.id.toolbar) LinearLayout toolbar;
    @BindView(R.id.search_list) RecyclerView searchList;
    @BindView(R.id.search_edit) EditText searchEdit;

    public interface SearchListener {
        void onReachSelected(int reachId);
        void onClose();
    }

    public SearchView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_search, this);
        onFinishInflate();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_search, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        searchList.setLayoutManager(new LinearLayoutManager(getContext()));

        clear.requestFocus();
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    return;
                }

                awApi.getReaches(s.toString()).subscribe(new DisposableSingleObserver<List<ReachSearchResult>>() {
                    @Override
                    public void onSuccess(@NonNull List<ReachSearchResult> reachSearchResults) {
                        updateResults(reachSearchResults);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setListener(SearchListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReachItemClick(int reachId) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);

        if (listener != null) {
            listener.onReachSelected(reachId);
        }
    }

    @OnClick(R.id.back)
    protected void onBackClick() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);

        if (listener != null) {
            listener.onClose();
        }
    }

    @OnClick(R.id.clear)
    protected void onClearClick() {
        searchEdit.setText("");
        updateResults(Lists.newArrayList());
    }

    private void updateResults(List<ReachSearchResult> results) {
        RunsAdapter adapter = (RunsAdapter)searchList.getAdapter();
        if (adapter == null) {
            searchList.setAdapter(new RunsAdapter(getContext(), results, this));
        } else {
            adapter.setSearchResults(results);
            adapter.notifyDataSetChanged();
        }
    }
}
