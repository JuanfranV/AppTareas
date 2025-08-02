package com.example.proyectotareas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private Button buttonAgregar;
    ArrayList<agregarTareaModel> listaTareas = new ArrayList<>();
    RecyclerView recyclerTareas;
    tareaAdapter adapter;
    ImageView imViFoto;
    public ActivityResultLauncher<Intent> agregarTareaLauncher;

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
        recyclerTareas.setLayoutManager(new LinearLayoutManager(this));

        buttonAgregar = findViewById(R.id.buttonAñadir);

        adapter = new tareaAdapter(this, listaTareas);
        recyclerTareas.setAdapter(adapter);

        agregarTareaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String accion = data.getStringExtra("accion");
                        int posicion = data.getIntExtra("posicion", -1);


                        if ("eliminar".equals(accion) && posicion != -1) {
                            listaTareas.remove(posicion);
                            adapter.notifyItemRemoved(posicion);
                        } else {
                            String titulo = data.getStringExtra("titulo");
                            String descripcion = data.getStringExtra("descripcion");
                            String estado = data.getStringExtra("estado");

                            if (titulo != null && descripcion != null && estado != null) {
                                if (posicion != -1) {
                                    agregarTareaModel tarea = listaTareas.get(posicion);
                                    tarea.setNombre(titulo);
                                    tarea.setDescripcion(descripcion);
                                    tarea.setCompletadoPendiente(estado);
                                    adapter.notifyItemChanged(posicion);
                                }else {
                                    listaTareas.add(new agregarTareaModel(titulo, descripcion, estado));
                                    adapter.notifyItemInserted(listaTareas.size() - 1);
                                }
                            }
                        }
                    }
                });

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AgregarTareaActivity.class);
                agregarTareaLauncher.launch(intent);
            }
        });

        imViFoto = findViewById(R.id.imViFoto);

        imViFoto.setOnClickListener( view ->{
            Intent intent = new Intent(MainActivity.this, fotoActivity.class);
            startActivity(intent);
        });
    }
}