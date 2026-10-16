package com.example.parcial_heroes

object Utils {
    object Rutas {
        const val LISTA = "lista"
        const val DETALLE = "detalle/{id}"

        fun detalle(id: Int) = "detalle/$id"
    }
}

