package ch.webing.ntb_client.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by antic-software-ing on 20.09.2017.
 */

public interface TokenEndpoints {
    @GET("api/validatetoken")
    Call<Void> validateToken(@Header("Authorization") String token);
}
