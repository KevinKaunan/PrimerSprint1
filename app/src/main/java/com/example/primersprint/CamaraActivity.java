package com.example.primersprint;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Objects;

@SuppressLint("Registered")
public class CamaraActivity extends AppCompatActivity {
    ImageView imageView;
    Button buttonAbrirCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        imageView = findViewById(R.id.imageviewCamara);
        buttonAbrirCamara = findViewById(R.id.buttonCamara);
        imageView.setImageResource(R.drawable.ic_image_black_24dp);

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
        }
    }

    public void volverGrid(View view) {
        Intent volverGrid = new Intent(this, MainActivity.class);
        startActivity(volverGrid);
    }
}