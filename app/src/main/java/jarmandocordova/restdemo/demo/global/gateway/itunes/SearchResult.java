package jarmandocordova.restdemo.demo.global.gateway.itunes;

import java.util.Arrays;

/**
 * Created by jarma on 9/25/2016.
 */

public class SearchResult {
    private ITunesTrack[] results = new ITunesTrack[]{};

    public ITunesTrack[] getResults() {
        return results;
    }

    public void setResults(ITunesTrack[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(results);
    }
}