package ch.webing.ntb_client.rest;

import ch.webing.ntb_client.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by antic-software-ing on 20.09.2017.
 */

public interface LoginEndpoints {

    @POST("api/login")
    Call<User> login(@Body User user);

}
