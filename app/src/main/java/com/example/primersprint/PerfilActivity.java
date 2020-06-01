package com.example.primersprint;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TableLayout;

import com.example.primersprint.ui.EditarPerfilActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PerfilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private TableLayout tableAlbumes;


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

        tableAlbumes = findViewById(R.id.tableLayout);

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

    //Métodos de generación de álbumes.

    public void crearAlbum(View view){

        Button button = new Button(this);
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    EditText nombreAlbum = findViewById(R.id.plain_text_input);
                    ViewGroup.LayoutParams cp = findViewById(R.id.buttonDefault).getLayoutParams();
                    button.setText(nombreAlbum.getText());
                    tableAlbumes.addView(button, cp);
                    button.setOnClickListener(PerfilActivity.this::irA_album);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:

                    break;
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Quieres añadir un nuevo álbum? (Para borrar un álbum haz un click largo en el álbum que desees borrar.)").setPositiveButton("Sí", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        button.setOnLongClickListener(v -> {
            tableAlbumes.removeView(button);
            return true;
        });

    }

    public void irA_album(View view){
        Intent album = new Intent(this, AlbumesActivity.class);
        startActivity(album);
    }

}
