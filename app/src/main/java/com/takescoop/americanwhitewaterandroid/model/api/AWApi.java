package com.takescoop.americanwhitewaterandroid.model.api;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.model.Article;
import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.model.Reach;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class AWApi {
    private AWApiService webService;

    private interface AWApiService {
        @GET("River/search/.json")
        Observable<List<ReachSearchResponse>> getReaches();

        @GET("River/geo-summary/.json")
        Observable<List<ReachSearchResponse>> getReachesByGeo();

//        @GET("River/state-summary/state/:stateabbreviation/.json")
//        Observable<ReachSearchResponse> getReachesByState();

        @GET("River/detail/id/:reachid/.json")
        Observable<ReachResponse> getReachDetail();

        @GET("Article/view/articleid/:articleid/.json")
        Observable<Article> getArticle();
    }

    protected AWApi() {
        setupRestAdapters();
    }

    private void setupRestAdapters() {
        webService = ApiUtils.getRestAdapter().create(AWApiService.class);
    }

    public Observable<List<ReachSearchResult>> getReaches(Filter filter) {
        return webService.getReaches().map(new Func1<List<ReachSearchResponse>, List<ReachSearchResult>>() {
            @Override public List<ReachSearchResult> call(List<ReachSearchResponse> reachSearchResponses) {
                List<ReachSearchResult> results = Lists.newArrayList();
                for (ReachSearchResponse response : reachSearchResponses) {
                    results.add(response.toModel());
                }
                return results;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ReachSearchResult>> getReaches(LatLngBounds bounds) {
        return webService.getReachesByGeo().map(new Func1<List<ReachSearchResponse>, List<ReachSearchResult>>() {
            @Override public List<ReachSearchResult> call(List<ReachSearchResponse> reachSearchResponses) {
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

        return webService.getReachDetail().map(new Func1<ReachResponse, Reach>() {
            @Override public Reach call(ReachResponse reachResponse) {
                return reachResponse.toModel();
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }
}
