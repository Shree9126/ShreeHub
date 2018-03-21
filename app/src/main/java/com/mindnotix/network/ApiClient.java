package com.mindnotix.network;

import android.util.Log;

import com.mindnotix.application.MyApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mindnotix.utils.Constant.BASE_URL;



/**
 * Created by Admin on 5/1/2017.
 */

public class ApiClient {


    private static final String TAG = "ApiClient";
    private static Retrofit retrofit = null;
    private final YouthHubApi apiInterface;


    public ApiClient(YouthHubApi apiInterface) {
        this.apiInterface = apiInterface;
    }


    public static YouthHubApi getInterface() {
        return getClient().create(YouthHubApi.class);
    }


    private static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(getHttpLoggingInterceptor().build())
                    //      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }
        return retrofit;
    }

    private static OkHttpClient.Builder getHttpLoggingInterceptor() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder();

    }

    public static Retrofit getClient(String BASE_URL) {
        if (retrofit == null) {

            Log.d(TAG, "getClient: " + BASE_URL);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(0, TimeUnit.SECONDS)
                    .cache(provideCache())
                    .readTimeout(0, TimeUnit.SECONDS);


            // add your other interceptor

            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

        }

        return retrofit;

    }

    private static Cache provideCache ()
    {
        Cache cache = null;
        try
        {
            cache = new Cache( new File( MyApplication.getInstance().getCacheDir(), "http-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e)
        {
            //Log.e(  "Could not create Cache!" );
        }
        return cache;
    }
}
