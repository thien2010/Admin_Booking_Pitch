package com.example.booking_pitch.data.repository;

import com.example.booking_pitch.data.model.AcountAdmin;
import com.example.booking_pitch.data.model.AddPitch;
import com.example.booking_pitch.data.model.LoginAdminAccount;
import com.example.booking_pitch.data.model.News;
import com.example.booking_pitch.data.model.PitchClass;
import com.example.booking_pitch.data.model.SpanBusyClass;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RequestAPI {
    //http://localhost:3000/api/pitch/getAllU
    //http://192.168.0.100:3000/api/pitch/getAllU
    //http://192.168.0.100:3000/api/pitch/getAllA
    //http://localhost:3000/api/pitch/getAllA0
    //http://localhost:3000/api/pitch/getAllA1
    //https://datn-2021.herokuapp.com/api/news/getAllNews
    //https://datn-2021.herokuapp.com/api/pitch/getPitchBusyByDate/15092021
    //https://datn-2021.herokuapp.com/api/pitch/createPitchByAdmin
    //https://datn-2021.herokuapp.com/api/pitchS/editPitchByAdmin/613c26e9f7be9c0023052006
    //https://datn-2021.herokuapp.com/api/pitch/deletePitchByAdmin/613ea76dcd10b00023552aab
   // https://datn-2021.herokuapp.com/api/pitch/deletePitchByAdmin/613e19215b96a40023a2e704
    //https://datn-2021.herokuapp.com/api/news/createNewsByAdmin
    //https://datn-2021.herokuapp.com/api/news/editNewsByAdmin/613f6c906fdb530023b0df1a
    //https://datn-2021.herokuapp.com/api/news/deleteNewsByAdmin/613f6c906fdb530023b0df1a
    //https://datn-2021.herokuapp.com/api/pitch/user/editPitch/



    @GET("deleteNewsByAdmin/{id}")
    Call<News> deleteNews(@Path("id")String id);

    @Multipart
    @POST("editNewsByAdmin/{id}")
    Call<News> updateNews(@Path("id") String id,
                                  @Part("title")RequestBody title,
                                  @Part("content")RequestBody content,
                                  @Part MultipartBody.Part image);

    @Multipart
    @POST("createNewsByAdmin")
    Call<News> createNews(@Part("title")RequestBody title,
                          @Part("content")RequestBody content,
                          @Part MultipartBody.Part image);

    @GET("deletePitchByAdmin/{id}")
    Call<AddPitch> deletePitch(@Path("id")String id);


    @Multipart
    @POST("editPitchByAdmin/{id}")
    Call<AddPitch> updateNewPitch(@Path("id") String id,
                                  @Part("pitchID")RequestBody pitchID,
                                 @Part("pitchName")RequestBody pitchName,
                                 @Part("price")RequestBody price,
                                 @Part("priceWater")RequestBody priceWater,
                                 @Part MultipartBody.Part image,
                                 @Part("detail")RequestBody detail,
                                 @Part("createBy")RequestBody createBy);

    @Multipart
    @POST("createPitchByAdmin")
    Call<AddPitch> creatNewPitch(@Part("pitchID")RequestBody pitchID,
                                 @Part("pitchName")RequestBody pitchName,
                                 @Part("price")RequestBody price,
                                 @Part("priceWater")RequestBody priceWater,
                                 @Part MultipartBody.Part image,
                                 @Part("detail")RequestBody detail,
                                 @Part("createBy")RequestBody createBy);
    //https://datn-2021.herokuapp.com/api/pitch/getAllA2
    @GET("getAllNews")
    Call<ResponeNews> getAllNew();
    @GET("getAllA2")
    Call<List<PitchClass>> getAllA2();
    //https://datn-2021.herokuapp.com/api/user/getAll
    @GET("getAll")
    Call<ResponeAllUser> getAllUser();

    @GET("getAllA0")
    Call<List<PitchClass>> getAllA0();

    @GET("getAllA1")
    Call<List<PitchClass>> getAllA1();

    @GET("getAllU")
    Call<ReponeAllSan> getAllU();

    @GET("getPitchBusyByDate/{date}")
    Call<ResponeSpanBusy> getAllBusy(@Path("date") String date);
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
    Call<PitchClass> updatePitch(@Path("id") String id, @Field("state") String state,@Field("typeBooking") String typeBooking);

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
