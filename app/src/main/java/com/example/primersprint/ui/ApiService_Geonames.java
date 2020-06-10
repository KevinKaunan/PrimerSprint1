package com.example.primersprint.ui;

import com.example.primersprint.ui.response.Geonames;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService_Geonames {
    //request particular
    //1 parametro:"lat="+latitude+"&"
    //2 parametro:"lng="+longuitud+"&"
    //3 dato:     "username=andreshdm"
    //lat=42.814948&lng=-1.639035&username=andreshdm
    @GET("findNearbyPlaceNameJSON")
    Call<Geonames> getCiudad(@Query("lat")String latitude, @Query("lng")String longitud, @Query("username")String usuario);
    //los datos json se parsean a instancia java de la clase Geonames

//    @GET("/api/users-collection")
//    Call<Geonames> getCiudad(@Query("username")String usuario);
//    //los datos json se parsean a instancia java de la clase Geonames

//    @FormUrlEncoded
//    @POST("auth/login")
//    Call<clase a crear pal login> doLogin(
//            @Field("email") String email,
//            @Field("password") String password);


}
