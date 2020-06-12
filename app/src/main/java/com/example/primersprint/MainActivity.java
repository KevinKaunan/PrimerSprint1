package com.example.primersprint;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.primersprint.ui.Api_Geopics;
import com.example.primersprint.ui.CustomAdapter;
import com.example.primersprint.ui.response.Image;
import com.example.primersprint.ui.response.PorDefecto;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    boolean datosRecogidos = true;
    SearchView sv;
    String rico="";
    TextView name;
    List<Image> lista =new ArrayList<>();
    String[] fruitNames = {"Apple","Orange","strawberry","Melon","Kiwi","Banana"};
    int[] fruitImages = {R.drawable.apple,R.drawable.oranges,R.drawable.strawberry,R.drawable.watermelon,R.drawable.kiwi,R.drawable.banana};
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv = findViewById(R.id.searchView);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //Petición de permiso para la ubicación.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1000);
        }

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

        // -->AITOR
        //CustomAdapter customAdapter = new CustomAdapter();
        customAdapter = new CustomAdapter(this.getApplicationContext(), R.layout.datos_columna_grid, lista);
        // <--AITOR

        gridView.setAdapter(customAdapter);
        // Permite visualizar las fotos al clicar en ellas.
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), GridItemActivity.class);
            intent.putExtra("name", lista.get(position).getNombre());
            intent.putExtra("image", R.drawable.banana);
            startActivity(intent);

        });
        getSupportActionBar().setTitle("GeoPics");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context,"APLICANDO FILTRO", duration);
                toast.show();
                rico = query;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, "ESTAS CAMBIANDO TEXTO?", duration);
                toast.show();
                rico = newText;
                return true;
            }
        });

        // -->AITOR
        verificar();
        // <--AITOR
    }

    public void verificar() {
        //verificamos con una llamada a la api si existen y coinciden los datos
        String filtro ="almendra";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        // -->AITOR
        Call<List<Image>> call = Api_Geopics.getApiService().getImgs(filtro);
        //Call<List<Image>> call = Api_Geopics.getApiService().getImages();
        // <--AITOR
        call.enqueue(new Callback<List<Image>>(){
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if(response.isSuccessful()){
                    /* // -->AITOR
                    datosRecogidos = true;
                    List<Image> listaImagenes= response.body();
                    for(int i=0;i<listaImagenes.size();i++){
                        lista.add(listaImagenes.get(i));
                        Log.d("Items", listaImagenes.get(i).getNombre());
                        //name.setText(i.getNombre());
                    }
                    Toast toast = Toast.makeText(context, "Successful", duration);
                    toast.show();
                    // <--AITOR */
                    // -->AITOR
                    lista = response.body();
                    for(Image img:lista) Log.d("Items", img.getNombre());
                    // https://stackoverflow.com/questions/4438128/how-to-refresh-a-gridview
                    // Esta forma no es la más elegante pero no quería cambiar el adaptador.
                    customAdapter = new CustomAdapter(context, R.layout.datos_columna_grid, lista);
                    gridView.invalidateViews();
                    gridView.setAdapter(customAdapter);
                    // <--AITOR
                }
                Log.d("onResponse ciudad",response.code()+"");
                if(!response.isSuccessful()) {
                    Toast toast = Toast.makeText(context, "Error, ha habido respuesta pero no la esperada", duration);
                    toast.show();
                    datosRecogidos =false;
                    Log.d("onResponse error",response.code()+"");
                }
            }
            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                datosRecogidos =false;
                Toast toast = Toast.makeText(context,"Mandas mensaje pero no hay respuesta", duration);
                toast.show();
                Log.d("onFailure error","Error: " + t.toString() );
            }
        });
    }


}
