package com.example.booking_pitch.data.repository;

import com.example.booking_pitch.data.model.AcountAdmin;
import com.example.booking_pitch.data.model.LoginAdminAccount;
import com.example.booking_pitch.data.model.PitchClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestAPI {
    //http://localhost:3000/api/pitch/getAllU
    //http://192.168.0.100:3000/api/pitch/getAllU
    //http://192.168.0.100:3000/api/pitch/getAllA
    //http://localhost:3000/api/pitch/getAllA0
    //http://localhost:3000/api/pitch/getAllA1
//    https://datn-2021.herokuapp.com/api/news/getAllNews

    @GET("getAllNews")
    Call<ResponeNews> getAllNew();
    @GET("getAllA")
    Call<List<PitchClass>> getAllA();

    @GET("getAllA0")
    Call<List<PitchClass>> getAllA0();

    @GET("getAllA1")
    Call<List<PitchClass>> getAllA1();

    @GET("getAllU")
    Call<ReponeAllSan> getAllU();

    //http://192.168.0.100/api/pitch/tk/date/21062021
    @GET("date/{date}")
    Call<ResponeGetDay> getDay(@Path("date") String date);

    @GET("month/{month}")
    Call<ResponeGetDay> getMonth(@Path("month") String month);

    @GET("year/{year}")
    Call<ResponeGetNam> getYear(@Path("year") String year);

    //http://192.168.0.100:3000/api/pitch/create
    @POST("create")
    Call<PitchClass> createUser(@Body PitchClass pitchClass);

    //http://192.168.0.100:3000/api/pitch/user/editPitch/60d889aeb90b493f9070a3ec

    @FormUrlEncoded
    @POST("editPitch/{id}")
    Call<PitchClass> updatePitch(@Path("id") String id, @Field("state") String state);

    //http://192.168.0.101:3000/api/user/loginAdmin

    @POST("loginAdmin")
    Call<LoginAdminAccount> loginAdmin(@Body LoginAdminAccount loginAdminAccount);

    //http://localhost:3000/api/user/changePassAdmin
    @FormUrlEncoded
    @POST("changePassAdmin")
    Call<AcountAdmin> change_password(@Field("userID") String userID,
                                      @Field("oldPassword") String oldPassword,
                                      @Field("newPassword") String newPassword);
}
