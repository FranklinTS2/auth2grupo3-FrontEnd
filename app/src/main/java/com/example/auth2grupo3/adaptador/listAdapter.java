package com.example.auth2grupo3.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.auth2grupo3.R;
import com.example.auth2grupo3.funciones.imageUtils;
import com.example.auth2grupo3.modelo.Modelo;
import com.example.auth2grupo3.modelo.ProductoModel;

import java.util.List;

public class listAdapter extends ArrayAdapter<ProductoModel> {

    private List<Modelo> myList;
    private Context myContext;
    private int resourceLayout;
    public listAdapter(@NonNull Context context, int resource, List<ProductoModel> objects) {
        super(context, resource, objects);

    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ProductoModel modelo = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_row,parent,false);
        }

        ImageView imagen = convertView.findViewById(R.id.imageView);
        imagen.setImageBitmap(imageUtils.decodeFromBase64(modelo.getImagenProducto()));

        TextView id = convertView.findViewById(R.id.textId);
        id.setText(String.valueOf(modelo.getId()));

        TextView nombre = convertView.findViewById(R.id.textNombre);
        nombre.setText(modelo.getNombre());

        TextView precio = convertView.findViewById(R.id.textPrecio);
        precio.setText(String.valueOf(modelo.getPrecio()));

        TextView categoria = convertView.findViewById(R.id.textCategoria);
        categoria.setText(modelo.getCategoria());




        return convertView;
    }
    /*public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View view = convertView;
        if(view == null)
            view = LayoutInflater.from(myContext).inflate(R.layout.item_row,null);

        Modelo modelo = myList.get(position);

        ImageView imagen = view.findViewById(R.id.imageView);
        imagen.setImageResource(modelo.getImagen());

        TextView id = view.findViewById(R.id.textId);
        id.setText(String.valueOf(modelo.getId()));

        TextView nombre = view.findViewById(R.id.textNombre);
        nombre.setText(modelo.getNombre());

        TextView precio = view.findViewById(R.id.textPrecio);
        nombre.setText(String.valueOf(modelo.getPrecio()));

        TextView categoria = view.findViewById(R.id.textCategoria);
        categoria.setText(modelo.getCategoria());

        return view;
    }*/


}
