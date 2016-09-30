package jarmandocordova.restdemo.demo.main.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jarmandocordova.restdemo.R;
import jarmandocordova.restdemo.demo.global.MyApp;
import jarmandocordova.restdemo.demo.global.gateway.itunes.ITunesTrack;
import jarmandocordova.restdemo.demo.main.gateway.MainITunesGateway;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @InjectView(R.id.rvArray)
    RecyclerView rvArray;
    @InjectView(R.id.tvResponse)
    TextView tvResponse;
    @InjectView(R.id.toProvider)
    ToggleButton toProvider;
    @InjectView(R.id.pbProgress)
    ProgressBar pbProgress;
    @InjectView(R.id.etTerm)
    EditText etTerm;

    MainITunesGateway mainITunesGateway;

    TracksRVAdapter tracksRvAdapter;
    String lastSuccesfulSearch = "";

    //Media Player
    MediaPlayer mp = new MediaPlayer();
    String currentUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        MyApp.from(this).getComponent().inject(this);

        mainITunesGateway = new MainITunesGateway(this);
        tracksRvAdapter = new TracksRVAdapter(new ITunesTrack[]{}, this);
        rvArray.setAdapter(tracksRvAdapter);
        rvArray.setLayoutManager(new LinearLayoutManager(this));

        RxTextView.textChangeEvents(etTerm)//
                .debounce(400, TimeUnit.MILLISECONDS)// default Scheduler is Computation
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<TextViewTextChangeEvent>() {
                    @Override
                    public void call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        //Toast.makeText(MainActivity.this, "Debounced", Toast.LENGTH_SHORT).show();
                        searchTracks();
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("error", throwable.getMessage());
                        String s = Log.getStackTraceString(throwable);
                        tvResponse.setText(getErrorString());
                    }
                })
                .subscribe();
    }

    @OnClick(R.id.toProvider)
    public void searchTracks() {
        String term = etTerm.getText().toString();
        boolean notEmpty = !(term == null) && !term.equals("");
        if (notEmpty) {
            tvResponse.setText("Please wait...");
        } else {
            if (lastSuccesfulSearch.equals("")) {
                tvResponse.setText(getSuccessString(lastSuccesfulSearch));
                return;
            } else {
                term = lastSuccesfulSearch;
            }
        }
        final Toast toast = Toast.makeText(this, "Fetching...", Toast.LENGTH_SHORT);
        toast.show();
        final String termx = term;

        if (!toProvider.isChecked()) {
            mainITunesGateway
                    .getJsonArray(term)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Action1<JsonArray>() {
                        @Override
                        public void call(JsonArray jsonArray) {
                            toast.cancel();
                            ITunesTrack[] arr = new Gson().fromJson(jsonArray, ITunesTrack[].class);
                            Log.i("RetrofitRX", Arrays.deepToString(arr));
                            tracksRvAdapter.setArray(arr);
                            tracksRvAdapter.notifyDataSetChanged();
                            lastSuccesfulSearch = termx;
                            tvResponse.setText(getSuccessString(lastSuccesfulSearch));
                        }
                    })
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            toast.cancel();
                            Log.e("error", throwable.getMessage());
                            String s = Log.getStackTraceString(throwable);
                            tvResponse.setText(getErrorString());
                        }
                    }).subscribe();
        } else {
            mainITunesGateway
                    .getJsonArray(term)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Action1<JsonArray>() {
                        @Override
                        public void call(JsonArray jsonElements) {
                            toast.cancel();
                            Log.i("RetrofitRX", "new Value");
                            String value = jsonElements.toString();
                            ITunesTrack track = new ITunesTrack();
                            track.setArtistName(value);
                            tracksRvAdapter.setArray(new ITunesTrack[]{track});
                            tracksRvAdapter.notifyDataSetChanged();
                            lastSuccesfulSearch = termx;
                            tvResponse.setText(getSuccessString(lastSuccesfulSearch));
                        }
                    })
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            toast.cancel();
                            Log.e("error", throwable.getMessage());
                            String s = Log.getStackTraceString(throwable);
                            tvResponse.setText(getErrorString());
                        }
                    })
                    .subscribe();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pbProgress.setVisibility(View.GONE);
        searchTracks();
    }

    @Override
    public void onClick(View v) {
        final TracksRVAdapter.MyViewHolder holder = (TracksRVAdapter.MyViewHolder) v.getTag();
        if (v.getTag() != null) {
            final String url = holder.getPreviewUrl();
            if (currentUrl.equals(url)) {
                if (mp.isPlaying()) {
                    mp.pause();
                    return;
                } else {
                    mp.start();
                    return;
                }
            }
            mp.stop();
            mp.release();
            mp = new MediaPlayer();
            pbProgress.setVisibility(View.VISIBLE);
            Observable.fromCallable(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    try {
                        mp.setDataSource(url);
                        mp.prepare();
                        mp.start();
                        currentUrl = url;
                        return true;
                    } catch (Exception e) {
                        Log.e("MUSIC-ADAPTER-ERROR", "prepare() failed");
                        return false;
                    }
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Log.e("", "", throwable);
                    currentUrl = "";
                    pbProgress.setVisibility(View.GONE);
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            pbProgress.setVisibility(View.GONE);
                        }
                    }).subscribe();
        }
    }

    private String getSuccessString(String term) {
        //RAW
        if (toProvider.isChecked()) {
            //no items
            if (tracksRvAdapter.getItemCount() == 0) {
                if (term == null || term.equals("")) {
                    return "No input text, no raw json.";
                }
            }
            return "Showing iTunes raw json for: " + term;
        }
        //TRACKS
        //no items
        if (tracksRvAdapter.getItemCount() == 0) {
            if (term == null || term.equals("")) {
                return "No input text, no tracks.";
            } else {
                return "Sorry, no tracks found for: " + term;
            }
        }
        return "Showing " + tracksRvAdapter.getItemCount() + " tracks for: " + term;
    }

    private String getErrorString() {
        return "Oops, error.";
    }

    private String getWaitString() {
        return "Please wait...";
    }
}