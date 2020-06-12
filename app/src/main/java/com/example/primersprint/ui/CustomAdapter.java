package com.example.primersprint.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.example.primersprint.R;
import com.example.primersprint.ui.response.Image;

import java.util.List;

// Adaptador personalizado para mostrar las fotos en el gridview
public class CustomAdapter extends BaseAdapter {


    private Context ctx;
    private int layoutTemplate;
    private List<Image> imageList;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Image> objects) {
        super();
        this.ctx = context;
        this.layoutTemplate = resource;
        this.imageList = objects;
    }


    @Override
    public int getCount() {

        return imageList.size();

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

        View view1 = LayoutInflater.from(ctx).inflate(layoutTemplate, parent, false);
        Image elementoActual = imageList.get(position);

        TextView name= view1.findViewById(R.id.fruits);
        name.setText(elementoActual.getNombre());

        ImageView image = view1.findViewById(R.id.images);
        byte[] decodedString = Base64.decode(elementoActual.getBase64(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(decodedByte);

        return view1;
    }
}

