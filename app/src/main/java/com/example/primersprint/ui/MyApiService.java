package com.example.primersprint.ui;

import com.example.primersprint.ui.response.Geonames;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApiService {
    //request particular
    //1 parametro:"lat="+latitude+"&"
    //2 parametro:"lng="+longuitud+"&"
    //3 dato:     "username=andreshdm"
    //lat=42.814948&lng=-1.639035&username=andreshdm
    @GET("findNearbyPlaceNameJSON?")
    Call<Geonames> getCiudad(@Query("lat")String latitude, @Query("lng")String longitud, @Query("username")String usuario);
    //los datos json se parsean a instancia java de la clase Geonames
}
