package com.example.auth2grupo3;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.auth2grupo3.adaptador.listAdapter;
import com.example.auth2grupo3.modelo.Modelo;

import java.util.ArrayList;
import java.util.List;

public class listaProductos extends AppCompatActivity {

    private ListView myListView;
    private List<Modelo> myLista = new ArrayList<>();
    listAdapter mAdapter;

    Modelo selectedProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_productos);


        myListView = (ListView) findViewById(R.id.listView);

        setLista();

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectedProducto = (Modelo) (myListView.getItemAtPosition(i));
                AlertDialog dialog = createDialog("¿Que desea realizar?",selectedProducto);
                dialog.show();


            }



        });
    }

    private void setLista() {
        // Esto solo es una imagen en formato base64 :)
        String imagen64Testeo = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";

        myLista.add(new Modelo(1,"Detergente",4.4,"Hogar",imagen64Testeo));

        myLista.add(new Modelo(2,"Smart Tv",250.4,"Electrónica",imagen64Testeo));

        mAdapter = new listAdapter(getApplicationContext(),0,myLista);

        myListView.setAdapter(mAdapter);
    }


    AlertDialog createDialog(String mensaje, Modelo lista){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Aqui abriremos el intent para modificar
                Intent modificar = new Intent(getApplicationContext(), ModificarProducto.class);


                modificar.putExtra("Id",String.valueOf(lista.getId()));
                modificar.putExtra("Nombre",lista.getNombre());
                modificar.putExtra("Precio",String.valueOf(lista.getPrecio()));
                modificar.putExtra("Categoria",lista.getCategoria());
                modificar.putExtra("Imagen",lista.getImagen());
                startActivity(modificar);

            }


        });
        builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog dialog = createDialogConfirmar("¿Desea continuar con la eliminación?");
                dialog.show();

            }


        });
        return builder.create();
    }

    AlertDialog createDialogConfirmar(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminarProducto();

            }


        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"No se elimino el producto",Toast.LENGTH_SHORT).show();
            }


        });
        return builder.create();
    }

    private void eliminarProducto(){
        Toast.makeText(getApplicationContext(),"Respuesta a eliminar positiva",Toast.LENGTH_SHORT).show();
    }
}
