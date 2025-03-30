package com.example.auth2grupo3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.auth2grupo3.RetroFit.ApiService;
import com.example.auth2grupo3.RetroFit.RetroFitClient;
import com.example.auth2grupo3.funciones.imageUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificarProducto extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText txtnombre, txtprecio, txtId;
    private ImageView imageView;
    private Spinner spcategoria;
    private Uri imagenUri;
    private String imagen64;
    ArrayAdapter<String> adapter;

    private final ApiService apiService = RetroFitClient.getRetrofitInstance().create(ApiService.class);
    String token;
    ClientManager clientManager;
    private Button btnModificar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            clientManager =  new ClientManager(getApplicationContext());
            token= "Bearer " + clientManager.getClientToken();
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_modificar_producto);

            txtnombre = findViewById(R.id.txtnombre);
            txtprecio = findViewById(R.id.txtprecio);
            imageView = findViewById(R.id.imagen);
            spcategoria = findViewById(R.id.spcategoria);
            txtId = findViewById(R.id.textIdM);

            btnModificar = (Button) findViewById(R.id.btnModificar);



            txtId.setEnabled(false);


            // Configurar categorías en el Spinner
            List<String> categorias = Arrays.asList("Electrónica", "Ropa", "Hogar", "Deportes");
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spcategoria.setAdapter(adapter);

            llenarDatosModificar();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();

        }

            // Acción para seleccionar imagen
            imageView.setOnClickListener(v -> seleccionarImagen());

            // Acción para guardar producto
            btnModificar.setOnClickListener(v -> modificarProducto());


    }

    private void llenarDatosModificar(){
        Intent previousIntent = getIntent();
        String idProducto = previousIntent.getStringExtra("Id");
        String nombreProducto = previousIntent.getStringExtra("Nombre");
        String precioProducto = previousIntent.getStringExtra("Precio");
        String categoriaProducto = previousIntent.getStringExtra("Categoria");
        String imagenProducto = previousIntent.getStringExtra("Imagen");

        int categoriaSeleccionada = adapter.getPosition(categoriaProducto);
        txtId.setText(idProducto);
        txtnombre.setText(nombreProducto);
        txtprecio.setText(precioProducto);
        imageView.setImageBitmap(imageUtils.decodeFromBase64(imagenProducto));
        spcategoria.setSelection(categoriaSeleccionada);
    }

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
    private void modificarProducto() {
        String nombre = txtnombre.getText().toString().trim();
        String precio = txtprecio.getText().toString().trim();
        String categoria = spcategoria.getSelectedItem().toString();

        if (nombre.isEmpty() || precio.isEmpty() || imagenUri == null) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }else{
            HashMap<String,String> body = new HashMap<>();
            int id = Integer.parseInt(txtId.getText().toString());
            body.put("nombre",nombre);
            body.put("precio",precio);
            body.put("categoria",categoria);
            body.put("imagen",imagen64);

            Call<String> call = apiService.actualizarProducto(token,id,body);
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
        Toast.makeText(this, "Producto guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void mostrarToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}