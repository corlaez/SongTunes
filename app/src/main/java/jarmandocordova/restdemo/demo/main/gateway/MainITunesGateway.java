package jarmandocordova.restdemo.demo.main.gateway;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import jarmandocordova.restdemo.demo.global.MyApp;
import jarmandocordova.restdemo.demo.global.gateway.itunes.ITunesApi;
import jarmandocordova.restdemo.demo.global.gateway.itunes.ITunesTrack;
import jarmandocordova.restdemo.demo.global.gateway.itunes.SearchResult;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by jarma on 9/27/2016.
 */

public class MainITunesGateway {
    @Inject
    ITunesApi iTunesApi;
    //TODO add LocalCache

    public MainITunesGateway(Context context){
        MyApp.from(context).getComponent().inject(this);
    }

    public Observable<JsonArray> getJsonArray(String term){
        if(iTunesApi == null){
            return Observable.just(new JsonArray());
        }
        return iTunesApi.getSearchResult(term).map(new Func1<JsonObject, JsonArray>() {
            @Override
            public JsonArray call(JsonObject jsonObject) {
                return jsonObject.getAsJsonArray("results");
            }
        });
    }

    public Observable<ITunesTrack[]> getTracksFromJsonArray(String term){
        if(iTunesApi == null){
            return Observable.just(new ITunesTrack[]{});
        }
        return getJsonArray(term).map(new Func1<JsonArray, ITunesTrack[]>() {
            @Override
            public ITunesTrack[] call(JsonArray jsonArray) {
                ITunesTrack[] arr = new Gson().fromJson(jsonArray, ITunesTrack[].class);
                return arr;
            }
        });
    }

    public Observable<SearchResult> getTracksFromApi(String term){
        if(iTunesApi == null){
            return Observable.just(new SearchResult());
        }
        return iTunesApi.getTracks(term);
    }
}
