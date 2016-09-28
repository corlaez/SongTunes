package jarmandocordova.restdemo.lib.retrofit2.main;

import com.google.gson.JsonObject;

import jarmandocordova.restdemo.demo.global.gateway.itunes.ITunesApi;
import jarmandocordova.restdemo.demo.main.gateway.ITunesProvider;
import jarmandocordova.restdemo.demo.global.gateway.itunes.SearchResult;
import rx.Observable;

/**
 * Created by jarma on 9/26/2016.
 */

public class ITunesRetrofitProvider implements ITunesProvider {
    private ITunesApi iTunesApiRetrofit;

    @Override
    public Observable<JsonObject> getSearchResult(String term) {
        return iTunesApiRetrofit.getSearchResult(term);
    }

    @Override
    public Observable<SearchResult> getTracks(String term) {
        return iTunesApiRetrofit.getTracks(term);
    }

    @Override
    public void inject(ITunesApi iTunesApiRetrofit) {
        this.iTunesApiRetrofit = iTunesApiRetrofit;
    }
}
