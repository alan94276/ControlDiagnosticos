package com.example.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.databinding.ActivitySingupBinding;

public class SingupActivity extends AppCompatActivity {

    ActivitySingupBinding binding;
    Database databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new Database(this);
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();
                if(email.equals("")||password.equals("")||confirmPassword.equals(""))
                    Toast.makeText(SingupActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);
                        if(checkUserEmail == false){
                            Boolean insert = databaseHelper.insertData(email, password);
                            if(insert == true){
                                Toast.makeText(SingupActivity.this, "Regístrates con éxito!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SingupActivity.this, "Registro fallido!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SingupActivity.this, "¡El usuario ya existe! Por favor Iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SingupActivity.this, "Contraseña invalida!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}