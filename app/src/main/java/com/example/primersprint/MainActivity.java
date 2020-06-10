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
    String[] fruitNames = {"Apple","Orange","strawberry","Melon","Kiwi","Banana"};
    int[] fruitImages = {R.drawable.apple,R.drawable.oranges,R.drawable.strawberry,R.drawable.watermelon,R.drawable.kiwi,R.drawable.banana};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv = findViewById(R.id.searchView);


        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_notifications)
                .build();
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

            name= view1.findViewById(R.id.fruits);
            ImageView image = view1.findViewById(R.id.images);

//            name.setText(fruitNames[position]);
//            image.setImageResource(fruitImages[position]);
            //aqui igual no es majo

                verificar(position);
                if(datosRecogidos){
                   //name.setText("Eres simba");
                   image.setImageResource(R.drawable.banana);
                }else{
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "ERROR DE CONEXION", duration);
                    toast.show();
                }



            return view1;
        }
    }
    public void verificar(int i) {

//verificamos con una llamada a la api si existen y coinciden los datos

        String filtro ="patata";

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Call<List<Image>> call = Api_Geopics.getApiService().getImgs(filtro);
        call.enqueue(new Callback<List<Image>>(){    @Override
        public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
            if(response.isSuccessful()){
                datosRecogidos = true;
                List<Image> lista= response.body();
                for(Image i: lista){
                    name.setText(i.getNombre());
                }

                Toast toast = Toast.makeText(context, "Successful", duration);
                toast.show();

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
            }});
    }
//


}
