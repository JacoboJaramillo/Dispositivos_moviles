package com.example.taller_rick_morty_1.Models

data class RickModels(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type : String?,
    val image: String
)

data class RickModelsResponse(val results: List<RickModels>)

data class RickModelCard(
    val name: String,
    val status: String
)