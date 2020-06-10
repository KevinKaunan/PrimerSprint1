package com.example.primersprint;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import com.example.primersprint.ui.Api_Geonames;
import com.example.primersprint.ui.Api_Geopics;
import com.example.primersprint.ui.response.Geoname;
import com.example.primersprint.ui.response.Geonames;
import com.example.primersprint.ui.response.PorDefecto;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.Nullable;
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
    EditText descripcion;
    String lati ;
    String longi ;
    String encoded;
    byte[] byteArray;
    Bitmap bitmap;
    boolean imagensubida = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        imageView = findViewById(R.id.imageviewCamara);
        buttonAbrirCamara = findViewById(R.id.buttonCamara);
        imageView.setImageResource(R.drawable.ic_image_black_24dp);
        direccion = findViewById(R.id.txtDireccion);
        descripcion = findViewById(R.id.editTextDescripcion);

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
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imageView.setImageBitmap(bitmap);
            //Localización de la foto, una vez que esta ha sido capturada.
            localizacion();
            registrarLocalizacion();

        }
    }

    public void volverGrid(View view) {
        codificarImagen();
        if(direccion.getText().toString() !="" && descripcion.getText().toString().length() > 1 && encoded.length() >1){
            enviar();
            if(imagensubida){
                Intent volverGrid = new Intent(this, MainActivity.class);
                startActivity(volverGrid);
            }else{
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "ERROR AL GUARDAR", duration);
                toast.show();
            }

        }


    }
    public void codificarImagen() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byteArray = byteArrayOutputStream .toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

    }
    public void decodificarImagen(View view) {
        byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //editText.setText(decodedByte.toString());
    }
    public void enviar() {

//verificamos con una llamada a la api si existen y coinciden los datos
        String nombre = descripcion.getText().toString();
        String nombreAlbum = "Default";
        String base64 = encoded;
        String localizacion = direccion.getText().toString();


        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Call<PorDefecto> call = Api_Geopics.getApiService().postImg(nombre,nombreAlbum,base64,localizacion);
        call.enqueue(new Callback<PorDefecto>(){    @Override
        public void onResponse(Call<PorDefecto> call, Response<PorDefecto> response) {
            if(response.isSuccessful()){
                PorDefecto r= response.body();


                Toast toast = Toast.makeText(context, r.getMessage(), duration);
                toast.show();

            }



            Log.d("onResponse ciudad",response.code()+"");
            if(!response.isSuccessful()) {
                Toast toast = Toast.makeText(context, "Error, ha habido respuesta pero no la esperada", duration);
                toast.show();
                imagensubida =false;
                Log.d("onResponse error",response.code()+"");
            }
        }



            @Override
            public void onFailure(Call<PorDefecto> call, Throwable t) {
                imagensubida =false;
                Log.d("onFailure error","Error: " + t.toString() );
            }});
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



        Call<Geonames> call = Api_Geonames.getApiService().getCiudad(lati,longi,Constantes.API_USERNAME);
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