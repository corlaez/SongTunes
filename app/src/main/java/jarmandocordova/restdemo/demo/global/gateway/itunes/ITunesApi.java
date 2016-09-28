package jarmandocordova.restdemo.demo.global.gateway.itunes;

import com.google.gson.JsonObject;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by jarma on 9/25/2016.
 */

public interface ITunesApi {
    String END = "https://itunes.apple.com";

    @GET("/search?entity=musicTrack&limit=25")
    Observable<JsonObject> getSearchResult(@Query("term") String term);

    @GET("/search?entity=musicTrack&limit=25")
    Observable<SearchResult> getTracks(@Query("term") String term);

}