package jarmandocordova.restdemo.demo.global.di.module;

import android.content.Context;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jarmandocordova.restdemo.demo.global.MyApp;
import jarmandocordova.restdemo.demo.global.gateway.itunes.ITunesApi;
import jarmandocordova.restdemo.demo.main.gateway.ITunesProvider;
import jarmandocordova.restdemo.lib.retrofit2.main.ITunesRetrofitProvider;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Application-wide dependencies.
 * <p/>
 * Now it only contains the injectable objects and has no direct pairing
 * to the injection targets - that's what components are now for.
 * <p/>
 * Created by mgouline on 23/04/15. Edited by jarma
 */
@Module
public class ApplicationModule {
    private final MyApp mApplication;

    private ITunesProvider iTunesProvider;
    private ITunesApi iTunesApiRetrofit;

    public ApplicationModule(MyApp application) {
        mApplication = application;

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());

        Retrofit retrofitITunes = new Retrofit.Builder().baseUrl(ITunesApi.END).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        iTunesApiRetrofit = retrofitITunes.create(ITunesApi.class);

        iTunesProvider = new ITunesRetrofitProvider();
        iTunesProvider.inject(iTunesApiRetrofit);
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ITunesProvider provideITunesProvider() {
        return iTunesProvider;
    }

    @Provides
    @Singleton
    ITunesApi provideITunesApiRetrofit() {
        return iTunesApiRetrofit;
    }

    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            System.out.println(
                    String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
                            request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            System.out.println(
                    String.format("Received response for %s in %.1fms%n%s", response.request().url(),
                            (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}
