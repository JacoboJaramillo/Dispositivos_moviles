package com.example.taller_rick_morty_1.Repository

import com.example.taller_rick_morty_1.Models.RickModelCard
import com.example.taller_rick_morty_1.Models.RickModels
import com.example.taller_rick_morty_1.Models.RickModelsResponse
import kotlinx.coroutines.CancellationException

class RickRepository(private val api: RickApiService) {
    suspend fun obtenerPersonajes(): Result<List<RickModels>> =
        try {
            val list = api.obtenerPersonajes()


            Result.success(list.results)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun obtenerPersonaje(id: Int): Result<RickModels>{
        return try{
            Result.success(api.obtenerPersonaje(id))
        } catch (e: CancellationException){
            throw e
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}