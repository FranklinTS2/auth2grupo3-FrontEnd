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

import com.example.auth2grupo3.RetroFit.ApiService;
import com.example.auth2grupo3.RetroFit.RetroFitClient;
import com.example.auth2grupo3.funciones.imageUtils;
import com.example.auth2grupo3.modelo.ProductoModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText txtnombre, txtprecio;
    private ImageView imageView;
    private Spinner spcategoria;
    private Uri imagenUri;

    private final ApiService apiService = RetroFitClient.getRetrofitInstance().create(ApiService.class);
    private String imagen64;
    String token;
    ClientManager clientManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo); // <<-- Faltaba esta línea

        clientManager =  new ClientManager(getApplicationContext());
        token= "Bearer " + clientManager.getClientToken();

        txtnombre = findViewById(R.id.txtnombre);
        txtprecio = findViewById(R.id.txtprecio);
        imageView = findViewById(R.id.imagen);
        spcategoria = findViewById(R.id.spcategoria);

        Button btnGuardar = findViewById(R.id.btnguardar);
        Button btnListado = findViewById(R.id.btnListado);

        // Configurar categorías en el Spinner
        List<String> categorias = Arrays.asList("Electrónica", "Ropa", "Hogar", "Deportes");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcategoria.setAdapter(adapter);

        // Acción para seleccionar imagen
        imageView.setOnClickListener(v -> seleccionarImagen());

        // Acción para guardar producto
        btnGuardar.setOnClickListener(v -> guardarProducto());

        btnListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),listaProductos.class);
                startActivity(intent);

            }
        });

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
                imagen64 = imageUtils.encodeToBase64(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para guardar el producto
    private void guardarProducto() {
        String nombre = txtnombre.getText().toString().trim();
        String precio =txtprecio.getText().toString().trim();
        String categoria = spcategoria.getSelectedItem().toString();

        if (nombre.isEmpty() || txtprecio.getText().toString().trim().isEmpty() || imagenUri == null) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }else{
            HashMap<String,String> body = new HashMap<>();
            body.put("nombre",nombre);
            body.put("precio",precio);
            body.put("categoria",categoria);
            body.put("imagen",imagen64);

            Call<String> call = apiService.registrarProducto(token,body);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.code()==401){
                        clientManager.clearClientData();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if(response.isSuccessful()){
                        String mensaje = response.body();
                        runOnUiThread(() -> mostrarToast(mensaje));;
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }

        // Aquí podrías guardar los datos en una base de datos o enviarlos a un servidor
    }

    private void mostrarToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
