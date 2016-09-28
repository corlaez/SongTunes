package jarmandocordova.restdemo.demo.global.gateway.itunes;

/**
 * Created by jarma on 9/27/2016.
 */

public class ITunesTrack {
    private String trackName;
    private String artistName;
    private String previewUrl;

    public ITunesTrack(){}

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @Override
    public String toString() {
        return trackName + " " + artistName + " " + previewUrl + "\n\n";
    }
}