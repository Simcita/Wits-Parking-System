package com.example.just;

import com.google.gson.JsonObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

public interface ApiService {
    @FormUrlEncoded
    @POST("register_user.php")
    Call<JsonObject> signUp(
            @Field("username") String username,
            @Field("password") String password
    );
}
