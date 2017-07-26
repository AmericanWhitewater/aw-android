package com.takescoop.americanwhitewaterandroid.model.api;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.model.Article;
import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.model.Gage;
import com.takescoop.americanwhitewaterandroid.model.Reach;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public enum AWApi {
    Instance;

    private static final String PHOTO_BASE_URL = "https://www.americanwhitewater.org/photos/archive/";
    private static final String ARTICLE_PHOTO_BASE_URL = "https://www.americanwhitewater.org/resources/images/abstract/";
    private static final String FLOW_GRAPH_URL = "https://www.americanwhitewater.org/content/Gauge2/graph/id/%s/metric/%d/.raw";

    private AWApiService webService;

    private interface AWApiService {
        @GET("River/search/.json")
        Single<List<ReachSearchResponse>> getReaches(
                @Query("river") String searchText,
                @Query("state") String state,
                @Query("level") String level,
                @Query("atleast") String difficultyLowerBound,
                @Query("atmost") String difficultyUpperBound);

        @GET("River/geo-summary/.json")
        Single<List<ReachSearchResponse>> getReachesByGeo(@Query("BBOX") String bounds);

        // ":" separated
        @GET("River/list/list/{reachIdList}/.json")
        Single<List<ReachSearchResponse>> getReachesList(@Path("reachIdList") String reachIdList);

        @GET("River/detail/id/{reachId}/.json")
        Single<ReachResponse> getReachDetail(@Path("reachId") Integer reachId);

        @Headers("accept-encoding: identity")
        @GET("News/all/type/frontpagenews/subtype//page/0/.json?limit=10")
        Single<ArticlesResponse> getArticlesList();

        @GET("Article/view/articleid/{articleId}/.json")
        Single<Article> getArticle(@Path("articleId") Integer articleId);
    }

    AWApi() {
        setupRestAdapters();
    }

    private void setupRestAdapters() {
        webService = ApiUtils.getRestAdapter().create(AWApiService.class);
    }

    public Single<List<ReachSearchResult>> getReaches(@Nullable String searchText) {
        return getReaches(searchText, null);
    }

    public Single<List<ReachSearchResult>> getReaches(@Nullable Filter filter) {
        return getReaches(null, filter);
    }

    public Single<List<ReachSearchResult>> getReaches(List<Integer> reachIds) {
        String reachIdsString = TextUtils.join(":", reachIds);
        return webService.getReachesList(reachIdsString)
                .map(reachSearchResponses -> {
                    List<ReachSearchResult> results = Lists.newArrayList();
                    for (ReachSearchResponse response : reachSearchResponses) {
                        results.add(response.toModel());
                    }
                    return results;
                }).observeOn(AndroidSchedulers.mainThread());
    }

    private Single<List<ReachSearchResult>> getReaches(@Nullable String searchText, @Nullable Filter filter) {
        return webService.getReaches(searchText, getRegions(filter), getFlowLevelApiCode(filter), getLowerDifficulty(filter), getUpperDifficulty(filter))
                .map(reachSearchResponses -> {
                    List<ReachSearchResult> results = Lists.newArrayList();
                    for (ReachSearchResponse response : reachSearchResponses) {
                        results.add(response.toModel());
                    }
                    return results;
                }).observeOn(AndroidSchedulers.mainThread());
    }


    public Single<List<ReachSearchResult>> getReaches(LatLngBounds bounds) {
        LatLng sw = bounds.southwest;
        LatLng ne = bounds.northeast;

        String boundsString = TextUtils.join(",", Lists.newArrayList(sw.longitude, sw.latitude, ne.longitude, ne.latitude));

        return webService.getReachesByGeo(boundsString).map(reachSearchResponses -> {
            List<ReachSearchResult> results = Lists.newArrayList();
            for (ReachSearchResponse response : reachSearchResponses) {
                results.add(response.toModel());
            }
            return results;
        }).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Reach> getReach(Integer reachId) {
        if (reachId == null || reachId == 0) {
            return Single.error(new Exception("No reach id"));
        }

        return webService.getReachDetail(reachId).map(ReachResponse::toModel).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Article>> getArticlesList() {
        return webService.getArticlesList().map(ArticlesResponse::getArticles).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Article> getArticle(Integer articleId) {
        return webService.getArticle(articleId).observeOn(AndroidSchedulers.mainThread());
    }

    public String getPhotoUrl(int photoId) {
        return PHOTO_BASE_URL + photoId + ".jpeg";
    }

    public String getArticlePhotoUrl(String articleId, String abstractPhotoNumber) {
        return ARTICLE_PHOTO_BASE_URL + articleId + "-" + abstractPhotoNumber + ".jpg";
    }

    public String getFlowGraphUrl(Gage gage) {
        if (gage.getAwGageMetricId() == null) {
            return "";
        }
        return String.format(Locale.US, FLOW_GRAPH_URL, gage.getId(), gage.getAwGageMetricId());
    }

    private String getRegions(@Nullable Filter filter) {
        //TODO add multiple region support
        String regionCode = null;
        if (filter != null && filter.getRegions() != null && filter.getRegions().size() > 0) {
            regionCode = filter.getRegions().get(0).getCode();
        }

        return regionCode;
    }

    private String getFlowLevelApiCode(@Nullable Filter filter) {
        String level = null;
        if (filter != null && filter.getFlowLevel() != null) {
            level = filter.getFlowLevel().getApiQueryCode();
        }

        return level;
    }

    private String getLowerDifficulty(@Nullable Filter filter) {
        String lowerDifficulty = null;
        if (filter != null && filter.getDifficultyLowerBound() != null) {
            lowerDifficulty = filter.getDifficultyLowerBound().getTitle();
        }

        return lowerDifficulty;
    }

    private String getUpperDifficulty(@Nullable Filter filter) {
        String upperDifficulty = null;
        if (filter != null && filter.getDifficultyUpperBound() != null) {
            upperDifficulty = filter.getDifficultyUpperBound().getTitle();
        }

        return upperDifficulty;
    }
}
