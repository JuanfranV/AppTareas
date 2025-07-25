package com.example.proyectotareas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    List<agregarTareaModel> listaTareas = new ArrayList<>();
    tareaAdapter adapter = new tareaAdapter(listaTareas);

    private final ActivityResultLauncher<Intent> agregarTareaLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Log.d("MainActivity", "Resultado recibido: resultCode=" + result.getResultCode());
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Intent data = result.getData();
                            String titulo = data.getStringExtra("titulo");
                            String descripcion = data.getStringExtra("descripcion");
                            String estado = data.getStringExtra("estado");
                            Log.d("MainActivity", "Datos: T=" + titulo + ", D=" + descripcion + ", E=" + estado);

                            if (titulo != null && descripcion != null && estado != null) {
                                listaTareas.add(new agregarTareaModel(titulo, descripcion, estado));
                                Log.d("MainActivity", "Tarea añadida. Nuevo tamaño lista: " + listaTareas.size());
                                adapter.notifyItemInserted(listaTareas.size() - 1);
                                recyclerTareas.scrollToPosition(listaTareas.size() - 1);
                            } else {
                                Log.e("MainActivity", "Alguno de los datos recibidos es nulo.");
                            }
                        }
                    });

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

        listaTareas = new ArrayList<>();
        adapter = new tareaAdapter(listaTareas);
        recyclerTareas.setAdapter(adapter);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AgregarTareaActivity.class);
                startActivity(intent);
            }
        });
    }
}