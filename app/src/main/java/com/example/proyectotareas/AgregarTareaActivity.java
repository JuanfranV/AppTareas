package com.example.proyectotareas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class AgregarTareaActivity extends AppCompatActivity {

    EditText edTeTitulo;
    EditText edTeDescripcion;
    CheckBox chBoPendiante;
    CheckBox chBoCompletado;
    Button buttonGuardar;
    Button buttonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_tarea);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edTeTitulo = findViewById(R.id.edTeTitulo);
        edTeDescripcion = findViewById(R.id.edTeDescripcion);
        chBoCompletado = findViewById(R.id.chBoCompletado);
        chBoPendiante = findViewById(R.id.chBoPendiente);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonCancelar = findViewById(R.id.buttonCancelar);


        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = edTeTitulo.getText().toString();
                String descripcion = edTeDescripcion.getText().toString();
                String estado = chBoCompletado.isChecked() ? "Completado" : chBoPendiante.isChecked() ? "Pendiente" : "Ninguno";

                Intent intent = new Intent(AgregarTareaActivity.this, MainActivity.class);
                intent.putExtra("titulo", titulo);
                intent.putExtra("descripcion", descripcion);
                intent.putExtra("estado", estado);
                startActivity(intent);
                finish();
            }
        });


    }
}