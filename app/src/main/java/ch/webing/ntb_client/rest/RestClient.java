package ch.webing.ntb_client.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ch.webing.ntb_client.constant.ContantsApi.*;

/**
 * Created by antic-software-ing on 20.09.2017.
 */

public class RestClient {


    private static Retrofit retrofitInstance = null;

    public static Retrofit getRestClient() {
        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitInstance;
    }

}
