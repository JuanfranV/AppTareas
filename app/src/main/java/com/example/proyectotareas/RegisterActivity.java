package com.example.proyectotareas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectotareas.caracters.AnalyticsHelper;
import com.example.proyectotareas.caracters.MyApp;

public class RegisterActivity extends AppCompatActivity {
    EditText edTEUser;
    EditText edTEPass;
    EditText edTEConfirm;
    private Button buttonCreate;
    private Button buttonBack;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DBHelper(this);

        edTEUser = findViewById(R.id.etUserReg);
        edTEPass = findViewById(R.id.etPassReg);
        edTEConfirm = findViewById(R.id.etPassConfirma);

        edTEPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edTEConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        buttonCreate = findViewById(R.id.btnCrearCuenta);
        buttonBack = findViewById(R.id.btnVolver);

        buttonCreate.setOnClickListener(v -> doRegister());
        buttonBack.setOnClickListener(v -> finish());
    }
    private void doRegister(){
        String user = edTEUser.getText().toString().trim();
        String pass = edTEPass.getText().toString();
        String confirm = edTEConfirm.getText().toString();

        if(user.isEmpty() || pass.isEmpty() || confirm.isEmpty()){
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!pass.equals(confirm)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        if(user.length() < 3 || pass.length() < 6){
            Toast.makeText(this, "El nombre de usuario debe tener al menos 3 caracteres y la contraseña al menos 6", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            if (db.userExists(user)) {
                Toast.makeText(this, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
                return;
            }

            long id = db.insertUser(user, pass.toCharArray());

            if (id > 0) {
                Bundle params = new Bundle();
                params.putString("username", user);
                MyApp.logEvent("register_success", params, this);

                AnalyticsHelper.logLogin(user);
                Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                MyApp.logEvent("register_failed", null, this);
                AnalyticsHelper.logLoginFailed(user);
                Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_LONG).show();
            }

        } catch (Exception  e){
            MyApp.logEvent("register_failed", null, this);
            Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
        }

    }
    }
