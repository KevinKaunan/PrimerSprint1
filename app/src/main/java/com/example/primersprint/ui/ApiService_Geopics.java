package com.example.primersprint.ui;
import com.example.primersprint.ui.response.Image;
import com.example.primersprint.ui.response.PorDefecto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService_Geopics {
    //crear la clase albumes
    @GET("albumes")
    Call<String> getAlbumes(@Query("user")String usuario);
    @FormUrlEncoded
    @POST("album")
    Call<PorDefecto> postAlbum(@Field("user")String usuario,@Field("nameA")String nombreAlbum,@Field("numeroFotos")int numero);
    @FormUrlEncoded
    @PUT("albumes")
    Call<String> putAlbumes(@Field("user")String usuario,@Field("nameA")String nombreAlbum);

    @DELETE("albumes")
    Call<String> deleteAlbumes(@Field("user")String usuario,@Field("nameA")String nombreAlbum);

    @GET("imgs")
    Call<List<Image>> getImgs(@Query("filter")String filtro);

    @FormUrlEncoded
    @POST("img")
    Call<PorDefecto> postImg(@Field("nombre")String nombre,@Field("nombreAlbum")String nombreAlbum,@Field("base64")String base64,@Field("localizacion")String localizacion);

    @PUT("img")
    Call<String> putImg(@Field("idImg")String idImg,@Field("descripcionImg")String descripcionImg);

    @DELETE("img")
    Call<String> deleteImg(@Field("idImg")String idImg);


    @GET("perfil")
    Call<String> getPerfil(@Query("user")String usuario);


    @PUT("perfil")
    Call<String> putPerfil(@Field("user")String usuario,@Field("descripcionPerfil")String descripcionPerfil);

    @DELETE("perfil")
    Call<String> deletePerfil(@Field("user")String usuario);

    @FormUrlEncoded
    @POST("login")
    Call<PorDefecto> postLogin(@Field("user") String usuario, @Field("pswrd") String contraseña);
    @FormUrlEncoded
    @POST("signin")
    Call<PorDefecto> postSignin(@Field("username")String nombreusuario,@Field("correo")String correo,@Field("contraseña")String contraseña);


//    @GET("/api/users-collection")
//    Call<Geonames> getCiudad(@Query("username")String usuario);
//    //los datos json se parsean a instancia java de la clase Geonames

//    @FormUrlEncoded
//    @POST("auth/login")
//    Call<clase a crear pal login> doLogin(
//            @Field("email") String email,
//            @Field("password") String password);
}
