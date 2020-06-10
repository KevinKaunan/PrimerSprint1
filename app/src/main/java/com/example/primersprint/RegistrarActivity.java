package com.example.primersprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.primersprint.ui.Api_Geopics;
import com.example.primersprint.ui.response.PorDefecto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarActivity extends AppCompatActivity {
EditText correostext;
EditText usuariotext;
EditText contraseñatext;
boolean respuestaRegistro = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
    }
    public void inicioSesion(View view){

        correostext  = findViewById(R.id.editCorreo);
        usuariotext = findViewById(R.id.editNombreUsu);
        contraseñatext = findViewById(R.id.editContra);
        if(correostext.getText().toString().length() > 1 && usuariotext.getText().toString().length() > 1 && contraseñatext.getText().toString().length() >1){
            verificar();
            if(respuestaRegistro){
                Intent i = new Intent(RegistrarActivity.this, LoginActivity.class);
                startActivity(i);
            }else{
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "ERROR DE REGISTRO", duration);
                toast.show();
            }

        }

    }
    public void verificar() {

//verificamos con una llamada a la api si existen y coinciden los datos
        String nombreusuario = usuariotext.getText().toString();
        String correo = correostext.getText().toString();
        String contraseña = contraseñatext.getText().toString();

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Call<PorDefecto> call = Api_Geopics.getApiService().postSignin(nombreusuario,correo,contraseña);
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
                respuestaRegistro =false;
                Log.d("onResponse error",response.code()+"");
            }
        }



            @Override
            public void onFailure(Call<PorDefecto> call, Throwable t) {
                respuestaRegistro =false;
                Log.d("onFailure error","Error: " + t.toString() );
            }});
    }

}
