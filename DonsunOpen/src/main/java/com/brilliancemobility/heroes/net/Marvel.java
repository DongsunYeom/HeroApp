package com.brilliancemobility.heroes.net;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by dongsun on 13/11/16.
 */

public interface Marvel {
	final String API_KEY = "0cde103adab1dfd9f4e8a3074808b492";

	@GET("/?method=flickr.photos.search&api_key=" + API_KEY + "&format=json&nojsoncallback=1&")
	Observable<TestResponse> getSearch(@Query("sort")String sort, @Query("text") String text);
}