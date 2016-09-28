package jarmandocordova.restdemo.demo.main.gateway;

import com.google.gson.JsonObject;

import jarmandocordova.restdemo.demo.global.gateway.itunes.ITunesApi;
import jarmandocordova.restdemo.demo.global.gateway.itunes.SearchResult;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by jarma on 9/25/2016.
 */

public interface ITunesProvider {
    void inject(ITunesApi iTunesApiRetrofit);

    Observable<JsonObject> getSearchResult(@Query("term") String term);

    Observable<SearchResult> getTracks(@Query("term") String term);
}