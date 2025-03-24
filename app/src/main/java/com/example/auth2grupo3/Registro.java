package com.example.auth2grupo3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Registro extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText txtnombre, txtprecio;
    private ImageView imageView;
    private Spinner spcategoria;
    private Uri imagenUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo); // <<-- Faltaba esta línea

        txtnombre = findViewById(R.id.txtnombre);
        txtprecio = findViewById(R.id.txtprecio);
        imageView = findViewById(R.id.imagen);
        spcategoria = findViewById(R.id.spcategoria);
        Button btnSeleccionarImagen = findViewById(R.id.btnimagen);
        Button btnGuardar = findViewById(R.id.btnguardar);

        // Configurar categorías en el Spinner
        List<String> categorias = Arrays.asList("Electrónica", "Ropa", "Hogar", "Deportes");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcategoria.setAdapter(adapter);

        // Acción para seleccionar imagen
        btnSeleccionarImagen.setOnClickListener(v -> seleccionarImagen());

        // Acción para guardar producto
        btnGuardar.setOnClickListener(v -> guardarProducto());
    }

    // Método para abrir la galería y seleccionar una imagen
    private void seleccionarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagenUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagenUri);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para guardar el producto
    private void guardarProducto() {
        String nombre = txtnombre.getText().toString().trim();
        String precio = txtprecio.getText().toString().trim();
        String categoria = spcategoria.getSelectedItem().toString();

        if (nombre.isEmpty() || precio.isEmpty() || imagenUri == null) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aquí podrías guardar los datos en una base de datos o enviarlos a un servidor
        Toast.makeText(this, "Producto guardado correctamente", Toast.LENGTH_SHORT).show();
    }
}
