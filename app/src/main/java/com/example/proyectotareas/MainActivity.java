package com.example.proyectotareas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectotareas.caracters.tareaAdapter;
import com.example.proyectotareas.model.agregarTareaModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerTareas;
    private Button buttonAgregar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerTareas = findViewById(R.id.recyclerTareas);
        buttonAgregar = findViewById(R.id.buttonAñadir);

        recyclerTareas.setLayoutManager(new LinearLayoutManager(this));

        List<agregarTareaModel> listaTareas = new ArrayList<>();

        tareaAdapter adapter = new tareaAdapter(listaTareas);
        recyclerTareas.setAdapter(adapter);

        String titulo = getIntent().getStringExtra("titulo");
        String descripcion = getIntent().getStringExtra("descripcion");
        String estado = getIntent().getStringExtra("estado");

        if (titulo != null && descripcion != null && estado != null) {
            listaTareas.add(new agregarTareaModel(titulo, descripcion, estado));
            adapter.notifyItemInserted(listaTareas.size() - 1);
        }

        recyclerTareas.setAdapter(new tareaAdapter(listaTareas));

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AgregarTareaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}