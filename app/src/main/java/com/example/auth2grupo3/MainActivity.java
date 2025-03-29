package com.example.auth2grupo3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.auth2grupo3.RetroFit.ApiService;
import com.example.auth2grupo3.RetroFit.RetroFitClient;
import com.example.auth2grupo3.modelo.UsuarioModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtCorreo, txtPassword;
    TextView txtRegistro;
    ClientManager clientManager;
    private final ApiService apiService = RetroFitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientManager = new ClientManager(getApplicationContext());
        setContentView(R.layout.activity_main);

        verificarSesion();

        btnLogin = findViewById(R.id.btnLogin);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword);
        txtRegistro = findViewById(R.id.btnIrARegistro);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();

            }
        });

        txtRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),registroUsuario.class);
                startActivity(intent);

            }
        });

    }

    private void verificarSesion() {
        String token = clientManager.getClientToken();

        if (token != null && !token.trim().isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), Registro.class); // Asegúrate de que esta es la pantalla correcta
            startActivity(intent);
            finish();
        }
    }


    public void Login(){
        if(txtCorreo.getText().toString().trim().isEmpty() || txtPassword.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_SHORT).show();

        }else{

            Call<UsuarioModel> call = apiService.login(
                    "password",
                    txtCorreo.getText().toString(),
                    txtPassword.getText().toString(),
                    "client1",
                    "secret1"
                    );
            call.enqueue(new Callback<UsuarioModel>() {
                @Override
                public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
                    if(response.isSuccessful()){
                        UsuarioModel usuarioModel =response.body();
                        clientManager.saveClientData(
                                usuarioModel.getId(),
                                usuarioModel.getUsername(),
                                usuarioModel.getToken());
                        Log.d("Retrofit", "Inicio de secion exitoso");
                        Intent intent =new Intent(getApplicationContext(),Registro.class);
                        startActivity(intent);
                    }else{
                        Log.e("Retrofit", "Fallo en la solicitud: " + response.message());
                    }
                }
                @Override
                public void onFailure(Call<UsuarioModel> call, Throwable t) {
                    Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                }
            });
        }

    }
}