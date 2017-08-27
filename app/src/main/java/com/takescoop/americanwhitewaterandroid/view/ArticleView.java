package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.Article;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleView extends LinearLayout {
    private final Article article;
    private final ArticleViewListener listener;

    @BindView(R.id.toolbar_title) TextView toolbarTitle;
    @BindView(R.id.toolbar) LinearLayout toolbar;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.author) TextView author;
    @BindView(R.id.detail) TextView detail;

    public interface ArticleViewListener {
        void onClose();
    }

    public ArticleView(Context context, Article article, ArticleViewListener listener) {
        super(context);

        this.article = article;
        this.listener = listener;
        LayoutInflater.from(context).inflate(R.layout.view_article, this);

        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        init(article);
    }

    @OnClick(R.id.back_tap_target)
    protected void onBack() {
        listener.onClose();
    }

    private void init(Article article) {
        toolbarTitle.setText(article.getTitle());
        title.setText(article.getTitle());

        detail.setText(Html.fromHtml("<br>" + article.getContents()));
        author.setText(article.getAuthor() + ", " + article.getPostedDisplay());

        // Scrolling movement method is required (as opposed to using a ScrollView, because articles
        // longer than 2500 characters only show with the scrolling movement method.  
        detail.setMovementMethod(new ScrollingMovementMethod());
        detail.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
