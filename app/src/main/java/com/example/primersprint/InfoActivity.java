package com.example.primersprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


    }

    public void irAperfil(View view){
        Intent perfil = new Intent(this, PerfilActivity.class);
        startActivity(perfil);
    }
}
