package com.takescoop.americanwhitewaterandroid.model.api;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.common.collect.Lists;
import com.takescoop.americanwhitewaterandroid.model.Article;
import com.takescoop.americanwhitewaterandroid.model.Filter;
import com.takescoop.americanwhitewaterandroid.model.Reach;
import com.takescoop.americanwhitewaterandroid.model.ReachSearchResult;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public enum AWApi {
    Instance;

    private static final String PHOTO_BASE_URL = "https://www.americanwhitewater.org/photos/archive/";

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

//        @GET("River/state-summary/state/:stateabbreviation/.json")
//        Single<ReachSearchResponse> getReachesByState();

        @GET("River/detail/id/{reachId}/.json")
        Single<ReachResponse> getReachDetail(@Path("reachId") Integer reachId);

        @GET("Article/view/articleid/{articleid}/.json")
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

    public String getPhotoUrl(int photoId) {
        return PHOTO_BASE_URL + photoId + ".jpeg";
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
