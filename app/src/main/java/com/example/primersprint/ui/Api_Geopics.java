package com.example.primersprint.ui;

import com.example.primersprint.Constantes;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Api_Geopics {

    private static ApiService_Geopics Api_Service;
    public static ApiService_Geopics getApiService() {

        // Creamos un interceptor y le indicamos el log level a usar
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        // Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);



        if (Api_Service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.API_GEOPICS_BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            Api_Service = retrofit.create(ApiService_Geopics.class);
        }

        return Api_Service;
    }

}
