package com.example.proyectotareas.caracters;

import com.example.proyectotareas.model.UsuarioModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("registrar")
    Call<String> registrar(@Body UsuarioModel user);

    @POST("login")
    Call<String> login(@Body UsuarioModel user);
}
