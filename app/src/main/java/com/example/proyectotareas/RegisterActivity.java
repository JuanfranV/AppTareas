package com.example.proyectotareas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectotareas.caracters.AnalyticsHelper;
import com.example.proyectotareas.caracters.ApiClient;
import com.example.proyectotareas.caracters.ApiService;
import com.example.proyectotareas.caracters.MyApp;
import com.example.proyectotareas.model.UsuarioModel;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText edTEUser;
    EditText edTEPass;
    EditText edTEConfirm;
    private Button buttonGoogle;
    private Button buttonBack;

    private FirebaseAuth auth;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edTEUser = findViewById(R.id.etUserReg);
        edTEPass = findViewById(R.id.etPassReg);
        edTEConfirm = findViewById(R.id.etPassConfirma);

        edTEPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edTEConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        auth = FirebaseAuth.getInstance();
        api = ApiClient.getClient().create(ApiService.class);

        buttonGoogle = findViewById(R.id.buttonRegisterG);
        buttonBack = findViewById(R.id.btnVolver);

        buttonGoogle.setOnClickListener(v -> doRegister());
        buttonBack.setOnClickListener(v -> finish());
    }
    private void doRegister(){
        String email = edTEUser.getText().toString();
        String pass = edTEPass.getText().toString();
        String confirm = edTEConfirm.getText().toString();

        if (!pass.equals(confirm)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(r -> llamarApiRegistro(email, pass))
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Firebase: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void llamarApiRegistro(String email, String pass) {
        UsuarioModel user = new UsuarioModel(email, pass);

        api.registrar(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(RegisterActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error API", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
