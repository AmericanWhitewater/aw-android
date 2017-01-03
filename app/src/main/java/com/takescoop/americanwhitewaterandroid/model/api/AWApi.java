package com.takescoop.americanwhitewaterandroid.model.api;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.model.Article;
import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.model.Reach;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public enum AWApi {
    Instance;

    private AWApiService webService;

    private interface AWApiService {
        @GET("River/search/.json")
        Observable<List<ReachSearchResponse>> getReaches(
                @Query("state") String state, @Query("level") String level,
                @Query("atleast") String difficultyLowerBound, @Query("atmost") String difficultyUpperBound);

        @GET("River/geo-summary/.json")
        Observable<List<ReachSearchResponse>> getReachesByGeo(@Query("BBOX") String bounds);

//        @GET("River/state-summary/state/:stateabbreviation/.json")
//        Observable<ReachSearchResponse> getReachesByState();

        @GET("River/detail/id/{reachId}/.json")
        Observable<ReachResponse> getReachDetail(@Path("reachId") Integer reachId);

        @GET("Article/view/articleid/{articleid}/.json")
        Observable<Article> getArticle(@Path("articleId") Integer articleId);
    }

    AWApi() {
        setupRestAdapters();
    }

    private void setupRestAdapters() {
        webService = ApiUtils.getRestAdapter().create(AWApiService.class);
    }

    public Observable<List<ReachSearchResult>> getReaches(Filter filter) {
        //TODO add multiple region support
        String regionCode = null;
        if (filter.getRegions().size() > 0) {
            regionCode = filter.getRegions().get(0).getCode();
        }

        String level = null;
        if (filter.getFlowLevel() != null) {
            level = filter.getFlowLevel().getApiQueryCode();
        }

        String lowerDifficulty = null;
        if (filter.getDifficultyLowerBound() != null) {
            lowerDifficulty = filter.getDifficultyLowerBound().getTitle();
        }

        String upperDifficulty = null;
        if (filter.getDifficultyUpperBound() != null) {
            upperDifficulty = filter.getDifficultyUpperBound().getTitle();
        }

        return webService.getReaches(regionCode, level, lowerDifficulty, upperDifficulty).map(new Func1<List<ReachSearchResponse>, List<ReachSearchResult>>() {
            @Override
            public List<ReachSearchResult> call(List<ReachSearchResponse> reachSearchResponses) {
                List<ReachSearchResult> results = Lists.newArrayList();
                for (ReachSearchResponse response : reachSearchResponses) {
                    results.add(response.toModel());
                }
                return results;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ReachSearchResult>> getReaches(LatLngBounds bounds) {
        LatLng sw = bounds.southwest;
        LatLng ne = bounds.northeast;

        String boundsString = TextUtils.join(",", Lists.newArrayList(sw.longitude, sw.latitude, ne.longitude, ne.latitude));

        return webService.getReachesByGeo(boundsString).map(new Func1<List<ReachSearchResponse>, List<ReachSearchResult>>() {
            @Override
            public List<ReachSearchResult> call(List<ReachSearchResponse> reachSearchResponses) {
                List<ReachSearchResult> results = Lists.newArrayList();
                for (ReachSearchResponse response : reachSearchResponses) {
                    results.add(response.toModel());
                }
                return results;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Reach> getReach(Integer reachId) {
        if (reachId == 0) {
            return Observable.empty();
        }

        return webService.getReachDetail(reachId).map(new Func1<ReachResponse, Reach>() {
            @Override public Reach call(ReachResponse reachResponse) {
                return reachResponse.toModel();
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }
}
