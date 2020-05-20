package com.example.primersprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
    }
    public void inicioSesion(View view){
        Intent i = new Intent(RegistrarActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
