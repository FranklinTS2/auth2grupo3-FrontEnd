package com.example.auth2grupo3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.auth2grupo3.RetroFit.ApiService;
import com.example.auth2grupo3.RetroFit.RetroFitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registroUsuario extends AppCompatActivity {

    EditText txtNombre,txtCorreo,txtPassword;
    Button btnRegistrar;

    private final ApiService apiService = RetroFitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_usuario);

        txtNombre = (EditText) findViewById(R.id.txtUsuarioNombre);
        txtCorreo = (EditText) findViewById(R.id.txtUsuarioCorreo);
        txtPassword = (EditText) findViewById(R.id.txtUsuarioPassword);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();

            }


        });

    }
    private void registrarUsuario() {
        if(txtNombre.getText().toString().trim().isEmpty() || txtCorreo.getText().toString().trim().isEmpty() || txtPassword.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_SHORT).show();
        }
        HashMap<String,String> body = new HashMap<>();
        body.put("mail",txtCorreo.getText().toString());
        body.put("password",txtPassword.getText().toString());

        Call<String> call = apiService.registrarUsuario(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Retrofit", "Usuario creado exitosamente");
                    Toast.makeText(registroUsuario.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    registroUsuario.this.finish();
                } else {
                    Log.e("Retrofit", "Ocurrió un problema");
                    Toast.makeText(registroUsuario.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Retrofit","Ocurrio un problemita"+t.getMessage());
            }
        });

    }
}