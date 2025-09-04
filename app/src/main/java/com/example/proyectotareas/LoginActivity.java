package com.example.proyectotareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectotareas.caracters.AnalyticsHelper;
import com.google.firebase.analytics.FirebaseAnalytics;

public class LoginActivity extends AppCompatActivity {

    private EditText edTEUser;
    private EditText edTEPass;
    private Button buttonLogin;
    private Button buttonRegister;
    private DBHelper db;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DBHelper(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        edTEUser = findViewById(R.id.etUser);
        edTEPass = findViewById(R.id.etPassword);
        edTEPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        buttonLogin = findViewById(R.id.btnLogin);
        buttonRegister = findViewById(R.id.btnRegister);

        buttonLogin.setOnClickListener(v -> doLogin());

        buttonRegister.setOnClickListener(v -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
    }

    private void doLogin(){
        String user = edTEUser.getText().toString().trim();
        char[] passChar = edTEPass.getText().toString().toCharArray();

        if(user.isEmpty() || passChar.length==0){
            Toast.makeText(this, "Usuario y contraseña son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            boolean ok = db.checklogin(user, passChar);
            if(ok){
                AnalyticsHelper.logLogin(user);
                Toast.makeText(this, "Bienvenido ", Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("username", user);
                startActivity(i);
                finish();

            }
        }catch (Exception e){
            AnalyticsHelper.logLoginFailed(e.getMessage());
            Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();

        }catch (Error e){
            AnalyticsHelper.logLoginFailed(e.getMessage());
            Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
        }
    }
}