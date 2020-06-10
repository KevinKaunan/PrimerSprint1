package com.example.primersprint;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.primersprint.ui.Api_Geopics;
import com.example.primersprint.ui.EditarPerfilActivity;
import com.example.primersprint.ui.response.PorDefecto;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private TableLayout tableAlbumes;

    TextView nombreUsuario;
    String nombreAlbum;
    boolean albumSubido = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        nombreUsuario = findViewById(R.id.textView2);
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

        getSupportActionBar().setTitle("Perfil (Desliza para ver el submenú)");

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
        final EditText input = new EditText(PerfilActivity.this);
        input.setHint("Nombre del álbum");
        DialogInterface.OnClickListener dialogClickListener1 = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    ViewGroup.LayoutParams cp = findViewById(R.id.buttonDefault).getLayoutParams();
                    button.setText(input.getText().toString());
                    nombreAlbum = button.getText().toString();
                    tableAlbumes.addView(button, cp);
                    button.setOnClickListener(PerfilActivity.this::irA_album);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
            if (nombreUsuario.getText().toString().length() > 1 && nombreAlbum.length() > 1) {
                try {
                        enviar();
                        if (albumSubido) {
                            Log.d("onSubido", "Subido con exito");
                        } else {
                            Context context = getApplicationContext();
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, "ERROR AL GUARDAR", duration);
                            toast.show();
                        }

                    }catch(Exception e){
                        System.out.println(e.getMessage());
                }
            }



        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setMessage("¿Quieres añadir un nuevo álbum? (Para borrar un álbum haz un click largo en el álbum que desees borrar.)").setPositiveButton("Sí", dialogClickListener1)
                .setNegativeButton("No", dialogClickListener1).show();

        //Acción que hará el botón al hacer un click largo en el.

        button.setOnLongClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener2 = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        tableAlbumes.removeView(button);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            };
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setMessage("¿Quieres borrar este álbum?").setPositiveButton("Sí", dialogClickListener2)
                    .setNegativeButton("No", dialogClickListener1).show();
            return true;
        });

    }
    public void enviar() {

//verificamos con una llamada a la api si existen y coinciden los datos
        String nombre = nombreUsuario.getText().toString();
        int numero = 0;



        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Call<PorDefecto> call = Api_Geopics.getApiService().postAlbum(nombre,nombreAlbum,numero);
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
                albumSubido =false;
                Log.d("onResponse error",response.code()+"");
            }
        }



            @Override
            public void onFailure(Call<PorDefecto> call, Throwable t) {
                albumSubido =false;
                Log.d("onFailure error","Error: " + t.toString() );
            }});
    }

    public void irA_album(View view){
        Intent album = new Intent(this, AlbumesActivity.class);
        startActivity(album);
    }

}
