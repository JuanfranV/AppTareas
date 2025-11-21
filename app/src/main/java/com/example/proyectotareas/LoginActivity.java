package com.example.proyectotareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectotareas.caracters.ApiClient;
import com.example.proyectotareas.caracters.ApiService;
import com.example.proyectotareas.model.UsuarioModel;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edUser, edPass;
    private Button buttonGoogle;

    private FirebaseAuth auth;
    private ApiService api;

    private Button buttonRegister;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        auth = FirebaseAuth.getInstance();
        api = ApiClient.getClient().create(ApiService.class);


        edUser = findViewById(R.id.etUser);
        edPass = findViewById(R.id.etPassword);
        edPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        buttonGoogle = findViewById(R.id.buttonLoginG);

        buttonRegister = findViewById(R.id.btnRegister);


        buttonGoogle.setOnClickListener(v -> loginEmail());

        buttonRegister.setOnClickListener(v -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
    }

    private void loginEmail(){
        String email = edUser.getText().toString();
        String pass = edPass.getText().toString();

        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(r -> llamarApiLogin(email, pass))
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Firebase: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );

    }

    private void llamarApiLogin(String email, String pass) {
        UsuarioModel user = new UsuarioModel(email, pass);

        api.login(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(LoginActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error API", Toast.LENGTH_SHORT).show();
            }
        });
    }

}