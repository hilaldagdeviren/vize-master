package com.mobil.hilaldagdeviren.vize;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface RetroInterface {

    @GET("venues/search")
    Call<FoursquareResponse> getVenueJson(
            @Query("client_id") String client_id,
            @Query("client_secret") String client_secret,
            @Query("v") String version,
            @Query("ll") String ll);
}
