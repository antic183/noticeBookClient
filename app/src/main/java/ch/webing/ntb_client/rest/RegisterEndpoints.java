package ch.webing.ntb_client.rest;

import ch.webing.ntb_client.model.Notice;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by antic-software-ing on 20.09.2017.
 */

public interface RegisterEndpoints {

    @POST("register")
    Call<Notice> signup();

}
