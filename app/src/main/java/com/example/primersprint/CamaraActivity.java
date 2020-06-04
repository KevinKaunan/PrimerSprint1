package com.example.primersprint;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.example.primersprint.ui.MyApiAdapter;
import com.example.primersprint.ui.response.Geoname;
import com.example.primersprint.ui.response.Geonames;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Objects;

@SuppressLint("Registered")
public class CamaraActivity extends AppCompatActivity {
    ImageView imageView;
    Button buttonAbrirCamara;
    private LocationManager ubicacion;
    TextView latitud;
    TextView longitud;
    TextView direccion;
    String lati ;
    String longi ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        imageView = findViewById(R.id.imageviewCamara);
        buttonAbrirCamara = findViewById(R.id.buttonCamara);
        imageView.setImageResource(R.drawable.ic_image_black_24dp);
        direccion = findViewById(R.id.txtDireccion);

        // Comprueba el permiso de la cámara en el dispositivo.
        if(ContextCompat.checkSelfPermission(CamaraActivity.this, Manifest.permission.CAMERA )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CamaraActivity.this, new String[]{
                    Manifest.permission.CAMERA
            },  100 );
        }

        buttonAbrirCamara.setOnClickListener(v -> {
            Intent abrirCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(abrirCamara, 100);
        });
    }

    // Al sacar una foto la visualiza.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            assert data != null;
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imageView.setImageBitmap(bitmap);
            //Localización de la foto, una vez que esta ha sido capturada.
            localizacion();
            registrarLocalizacion();

        }
    }

    public void volverGrid(View view) {
        Intent volverGrid = new Intent(this, MainActivity.class);
        startActivity(volverGrid);
    }

    //Métodos para gestionar la localización.

    private void localizacion() {

        latitud = findViewById(R.id.txtLatitud);
        longitud = findViewById(R.id.txtLongitud);
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (loc != null) {
            latitud.setText("Ubicación no encontrada");
            longitud.setText("Ubicación no encontrada");
        }

    }

    private void registrarLocalizacion() {
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        ubicacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, new miLocalizacionListener());

    }
    public void localizar() {


//        accionamos la peticion y guardamos la respuesta con el tipo parseado de json
//        cambair a <Geonames>



        Call<Geonames> call = MyApiAdapter.getApiService().getCiudad(lati,longi,Constantes.API_USERNAME);
        call.enqueue(new Callback<Geonames>(){    @Override
        public void onResponse(Call<Geonames> call, Response<Geonames> response) {
            if(response.isSuccessful()){
                Geonames geonames = response.body();
                List<Geoname> lista = geonames.getGeonames();
                String concat = "";
                for(int i = 0; i<lista.size();i++) {

                    concat += lista.get(i).getCountryName() +"\n";
                    concat += lista.get(i).getToponymName() +"\n";
                    concat += lista.get(i).getPopulation() +"\n";
                    concat += lista.get(i).getDistance() +"\n";

                    direccion.setText("");
                    direccion.setText(concat);
                }



                Log.d("onResponse ciudad",geonames.toString());
//                    for(Geonames geo: lista){
//                        String content  ="";
//                        content += "Pais" + geo.getCountryName() + "\n";
//                        content += "Zona" + geo.getToponymName();
//                        locText.append(content);
//                    }
                Log.d("onResponse ciudad","");
            }
            if(!response.isSuccessful()) {
                latitud.setText("codigo" + response.code());
                Log.d("onResponse error","");
            }
        }

            @Override
            public void onFailure(Call<Geonames> call, Throwable t) {
                Log.d("onFailure ciudad","aqui ciudad.tostring");
            }});
    }


    private class miLocalizacionListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            direccion.setText("");
            latitud.setText(String.valueOf(location.getLatitude()));
            longitud.setText(String.valueOf(location.getLongitude()));
            lati = latitud.getText().toString();
            longi = longitud.getText().toString();
            localizar();
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}