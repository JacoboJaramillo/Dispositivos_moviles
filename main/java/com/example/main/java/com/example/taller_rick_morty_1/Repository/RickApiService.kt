package com.example.taller_rick_morty_1.Repository

import android.util.Log
import com.example.taller_rick_morty_1.Models.RickModels
import com.example.taller_rick_morty_1.Models.RickModelsResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface RickApiService {
    //Peticion para pedir todos los personajes
    @GET("character")
    suspend fun obtenerPersonajes(): RickModelsResponse
     //Peticion para pedir un personaje por medio del id de RickModels (personaje)
    @GET("character/{id}")
    suspend fun obtenerPersonaje(
         @Path("id") id: Int
    ): RickModels




}