package com.example.primersprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.primersprint.ui.Api_Geonames;
import com.example.primersprint.ui.Api_Geopics;
import com.example.primersprint.ui.response.Geoname;
import com.example.primersprint.ui.response.Geonames;
import com.example.primersprint.ui.response.PorDefecto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText usuarioText;
    EditText contraseñaText;
    boolean respuestaLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void irAperfil(View view){
        usuarioText = findViewById(R.id.editUsu);
        contraseñaText = findViewById(R.id.editContra);
        if(usuarioText.getText().toString().length() > 1){
            verificar();
            if(respuestaLogin){
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }else{
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "USUARIO INCORRECTO", duration);
                toast.show();
            }

        }

    }
    public void irAregistro(View view){
        Intent i = new Intent(LoginActivity.this, RegistrarActivity.class);
        startActivity(i);
    }

    public void verificar() {

//verificamos con una llamada a la api si existen y coinciden los datos
        String usuario = usuarioText.getText().toString();
        String contraseña = contraseñaText.getText().toString();

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Call<PorDefecto> call = Api_Geopics.getApiService().postLogin(usuario,contraseña);
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
                respuestaLogin=false;
                Log.d("onResponse error",response.code()+"");
            }
        }



            @Override
            public void onFailure(Call<PorDefecto> call, Throwable t) {
                respuestaLogin=false;
                Log.d("onFailure error","Error: " + t.toString() );
            }});
    }


}
