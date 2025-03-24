package com.example.auth2grupo3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class registroUsuario extends AppCompatActivity {

    EditText txtNombre,txtCorreo,txtPassword;
    Button btnRegistrar;

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
    }
}