package com.example.primersprint;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.primersprint.ui.EditarPerfilActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.gridlayout.widget.GridLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PerfilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private GridView gridAlbumes;

    String[] fruitNames = {"Default"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_perfil, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        setNavigationViewListener();

        getSupportActionBar().setTitle("Perfil");

        gridAlbumes = findViewById(R.id.gridAlbumes);

        ImageButton imageButton = new ImageButton(this);
        imageButton.setImageResource(R.drawable.ic_menu_gallery);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__perfil, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

// Métodos que hacen funcionar los diferentes botones del navigation drawer.

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            DrawerLayout mDrawerLayout= findViewById(R.id.drawer_layout);
            switch (item.getItemId()) {
                case R.id.nav_gallery: {
                    Intent cerrarSes = new Intent(this, LoginActivity.class);
                    startActivity(cerrarSes);
                    break;
                }
                case R.id.nav_slideshow: {
                    Intent info = new Intent(this, InfoActivity.class);
                    startActivity(info);
                    break;
                }
                case R.id.nav_home: {
                    Intent editarPerfil = new Intent(this, EditarPerfilActivity.class);
                    startActivity(editarPerfil);
                    break;
                }
            }
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
    }
    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void irAmenu(View view){
        Intent menu = new Intent(this, MainActivity.class);
        startActivity(menu);

    }
    public void crearAlbum(View view){
        ImageButton imagebutton = new ImageButton(this);
        imagebutton.setImageResource(R.drawable.ic_menu_gallery);
        imagebutton.setOnClickListener(this::irA_album);

    }

    public void irA_album(View view){
        Intent album = new Intent(this, AlbumesActivity.class);
        startActivity(album);
    }

    // Adaptador personalizado para mostrar las fotos en el gridview
//    private class CustomAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return fruitImages.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view1 = getLayoutInflater().inflate(R.layout.datos_columna_grid, null);
//
//            TextView name= view1.findViewById(R.id.fruits);
//            ImageView image = view1.findViewById(R.id.images);
//
//            name.setText(fruitNames[position]);
//            image.setImageResource(fruitImages[position]);
//
//
//            return view1;
//        }
//    }

}
