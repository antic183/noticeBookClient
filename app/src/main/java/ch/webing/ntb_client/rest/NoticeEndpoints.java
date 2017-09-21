package ch.webing.ntb_client.rest;

import java.util.List;

import ch.webing.ntb_client.model.Notice;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by antic-software-ing on 20.09.2017.
 */

public interface NoticeEndpoints {

    @GET("api/notices")
    Call<List<Notice>> getNotes(@Header("Authorization") String token);

    @GET("api/notices/{id}")
    Call<Notice> getNote(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/notices")
    Call<Notice> addNote(@Header("Authorization") String token, @Body Notice notice);

    @PUT("api/notices/{id}")
    Call<Notice> updateNote(@Header("Authorization") String token, @Path("id") int id, Notice notice);

    @DELETE("api/notices/{id}")
    Call<Notice> deleteNote(@Header("Authorization") String token, @Path("id") int id);
}
