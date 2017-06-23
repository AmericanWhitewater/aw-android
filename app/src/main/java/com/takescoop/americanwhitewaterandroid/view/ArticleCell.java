package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.takescoop.americanwhitewaterandroid.AWProvider;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.model.Article;
import com.takescoop.americanwhitewaterandroid.model.api.AWApi;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleCell extends RelativeLayout {
    private static final AWApi awApi = AWProvider.Instance.awApi();

    @BindView(R.id.title) TextView title;
    @BindView(R.id.detail) TextView detail;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.author) TextView author;

    public ArticleCell(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.cell_article, this);
        onFinishInflate();
    }

    public ArticleCell(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.cell_article, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    public void display(Article article) {
        title.setText(article.getTitle());
        detail.setText(article.get_abstract());

        String photoUrl = awApi.getArticlePhotoUrl(article.getArticleId(), article.getAbstractPhoto());
        Picasso.with(getContext()).load(photoUrl).into(image);

        author.setText(article.getAuthor() + ", " + article.getPostedDisplay());
    }
}