package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.Article;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

public class NewsFeedView extends RelativeLayout {
    private static final AWApi awApi = AWProvider.Instance.awApi();
    private NewsFeedListener listener;

    @BindView(R.id.progressWheel) ProgressBar progressWheel;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.articles_list) RecyclerView articlesList;

    public interface ArticleItemClickListener {
        void onArticleClick(Article article);
    }

    public interface NewsFeedListener {
        void onArticleSelected(Article article);
        void onReadMoreClicked();
    }

    public NewsFeedView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_news_feed, this);
        onFinishInflate();
    }

    public NewsFeedView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_news_feed, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        articlesList.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeContainer.setOnRefreshListener(this::updateArticles);
        swipeContainer.setColorSchemeResources(R.color.primary);

        updateArticles();
    }

    public void setListener(NewsFeedListener listener) {
        this.listener = listener;
    }

    private void onDonateViewClicked() {
        if (listener != null) {
            listener.onReadMoreClicked();
        }
    }

    private void updateArticles() {
        swipeContainer.setRefreshing(true);
        awApi.getArticlesList().subscribe(new DisposableSingleObserver<List<Article>>() {
            @Override public void onSuccess(@NonNull List<Article> articles) {
                swipeContainer.setRefreshing(false);
                displayArticles(articles);
            }

            @Override public void onError(@NonNull Throwable e) {
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void displayArticles(List<Article> articles) {
        articlesList.setAdapter(new ArticleAdapter(getContext(), articles, v -> onDonateViewClicked(), article -> {
            if (listener != null) {
                listener.onArticleSelected(article);
            }
        }));
    }

    private static class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private enum ViewType {
            Header, Cell;
        }

        private Context context;
        private List<Article> articles;
        private ArticleItemClickListener itemClickListener;
        private OnClickListener donateClickListener;

        public ArticleAdapter(Context context, List<Article> articles, OnClickListener donateClickListener, ArticleItemClickListener itemClickListener) {
            this.context = context;
            this.articles = articles;
            this.donateClickListener = donateClickListener;
            this.itemClickListener = itemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ViewType.Header.ordinal()) {
                return new DonateViewHolder(new DonateView(context));
            }

            ArticleCell cell = new ArticleCell(context);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cell.setLayoutParams(lp);

            return new ArticleCellViewHolder(cell);
        }

        @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position == 0) {
                ((DonateViewHolder) holder).getDonateView().setOnClickListener(donateClickListener);
                return;
            }

            Article article = articles.get(position - 1); // Account for header
            ArticleCell cell = ((ArticleCellViewHolder) holder).getArticleCell();
            cell.display(article);

            cell.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onArticleClick(article);
                }
            });
        }

        @Override public int getItemCount() {
            return articles.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return ViewType.Header.ordinal();
            }

            return ViewType.Cell.ordinal();
        }

        class DonateViewHolder extends RecyclerView.ViewHolder  {
            private DonateView donateView;

            public DonateViewHolder(DonateView itemView) {
                super(itemView);

                this.donateView = itemView;
            }

            public DonateView getDonateView() {
                return donateView;
            }
        }

        class ArticleCellViewHolder extends RecyclerView.ViewHolder {
            private ArticleCell articleCell;

            public ArticleCellViewHolder(ArticleCell articleCell) {
                super(articleCell);

                this.articleCell = articleCell;
            }

            public ArticleCell getArticleCell() {
                return articleCell;
            }
        }
    }
}
