package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.common.collect.Lists;
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
    @BindView(R.id.articles_list) RecyclerView articlesList;

    public interface ArticleItemClickListener {
        void onArticleClick(Article article);
    }

    public interface NewsFeedListener {
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

        progressWheel.setVisibility(VISIBLE);
        awApi.getArticlesList().subscribe(new DisposableSingleObserver<List<Article>>() {
            @Override public void onSuccess(@NonNull List<Article> articles) {
                progressWheel.setVisibility(GONE);
                displayArticles(articles);
            }

            @Override public void onError(@NonNull Throwable e) {
                progressWheel.setVisibility(GONE);
            }
        });
    }

    public void setListener(NewsFeedListener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.donate_view)
    protected void onDonateViewClicked() {
        if (listener != null) {
            listener.onReadMoreClicked();
        }
    }

    private void displayArticles(List<Article> articles) {
        articlesList.setAdapter(new ArticleAdapter(getContext(), articles, article -> {
            // TODO
        }));
    }

    private class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleCellViewHolder> {
        private Context context;
        private List<Article> articles;
        private ArticleItemClickListener itemClickListener;

        public ArticleAdapter(Context context, List<Article> articles, ArticleItemClickListener itemClickListener) {
            this.context = context;
            this.articles = articles;
            this.itemClickListener = itemClickListener;
        }

        @Override
        public ArticleAdapter.ArticleCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ArticleCell cell = new ArticleCell(context);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cell.setLayoutParams(lp);

            return new ArticleCellViewHolder(cell);
        }

        @Override public void onBindViewHolder(ArticleCellViewHolder holder, int position) {
            Article article = articles.get(position);
            holder.getArticleCell().display(article);

            holder.getArticleCell().setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onArticleClick(article);
                }
            });
        }

        @Override public int getItemCount() {
            return articles.size();
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
