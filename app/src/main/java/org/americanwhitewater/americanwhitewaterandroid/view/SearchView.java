package org.americanwhitewater.americanwhitewaterandroid.view;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.common.collect.Lists;
import org.americanwhitewater.americanwhitewaterandroid.AWProvider;
import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.model.ReachSearchResult;
import org.americanwhitewater.americanwhitewaterandroid.model.api.AWApi;
import org.americanwhitewater.americanwhitewaterandroid.utility.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;

public class SearchView extends LinearLayout implements RunsAdapter.ItemClickListener {
    AWApi awApi = AWProvider.Instance.awApi();
    private SearchListener listener;

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

        searchEdit.requestFocus();
        searchEdit.postDelayed(() -> ViewUtils.showKeyboard(getContext(), searchEdit), 100);

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

    @OnClick(R.id.back_tap_target)
    protected void onBackClick() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);

        if (listener != null) {
            listener.onClose();
        }
    }

    @OnClick(R.id.clear_tap_target)
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
