package com.example.primersprint;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    GridView gridView;


    String[] fruitNames = {"Apple","Orange","strawberry","Melon","Kiwi","Banana"};
    int[] fruitImages = {R.drawable.apple,R.drawable.oranges,R.drawable.strawberry,R.drawable.watermelon,R.drawable.kiwi,R.drawable.banana};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



// En esta expresión lambda se verifica cual de las id's ha sido pulsada, si la cámara o el perfil, para así hacer una acción u otra.
            navView.setOnNavigationItemSelectedListener((item) -> {
            if (item.getItemId() == R.id.navigation_perfil){
                Intent abrirPerfil = new Intent(this, PerfilActivity.class);
                startActivity(abrirPerfil);
            }

            if (item.getItemId() == R.id.navigation_home){
                Intent abrirActivityCamara = new Intent(this, CamaraActivity.class);
                startActivity(abrirActivityCamara);

            }

            return false;
        });

        gridView = findViewById(R.id.gridview);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
// Permite visualizar las fotos al clicar en ellas.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), GridItemActivity.class);
                intent.putExtra("name", fruitNames[position]);
                intent.putExtra("image", fruitImages[position]);
                startActivity(intent);

            }
        });
        getSupportActionBar().setTitle("GeoPics");
    }

// Adaptador personalizado para mostrar las fotos en el gridview
    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return fruitImages.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.datos_columna_grid, null);

            TextView name= view1.findViewById(R.id.fruits);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(fruitNames[position]);
            image.setImageResource(fruitImages[position]);


            return view1;
        }
    }
//


}
