package com.takescoop.americanwhitewaterandroid.model.api;


import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.takescoop.americanwhitewaterandroid.model.Article;

import java.util.List;

public class ArticlesResponse {
    @SerializedName("articles")
    @Expose
    public Response articles;

    public List<Article> getArticles() {
        return articles.getArticles();
    }

    public class Response {

        @SerializedName("CArticleGadgetJSON_view")
        @Expose
        public Article cArticleGadgetJSONView;
        @SerializedName("CArticleGadgetJSON_view_list")
        @Expose
        public List<Article> articles = Lists.newArrayList();

        public List<Article> getArticles() {
            return articles;
        }
    }
}
