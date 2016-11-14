package com.brilliancemobility.heroes.net;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by dongsun on 13/11/16.
 */

public class RetrofitFactory {
    private static final String WEB_SERVICE_BASE_URL = "https://api.flickr.com/services/rest";

    private static RestAdapter retrofit;

    public static RestAdapter getRetrofit() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            retrofit = new RestAdapter.Builder()
                    .setEndpoint(WEB_SERVICE_BASE_URL)
                    .setClient(new OkClient(okHttpClient))
                    .build();
        }
        return retrofit;
    }
    //endregion
}
