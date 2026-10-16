package com.example.parcial_heroes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun HeroImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        contentScale = ContentScale.Crop
    )
}


@Composable
fun HeroInfo(
    hero: Nombres,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "ID: ${hero.id}",
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = hero.nombre,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = hero.descripcion,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Superpoderes: ${hero.superPoderes.joinToString()}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun HeroInfoDetail(
    hero: Nombres,
    modifier: Modifier = Modifier
) {
    val perfilTacticoDetallado = heroProfile(hero.superPoderes)
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Bloque principal destacado

        // Estadisticas del heroe
        Text(
            text = "Perfil Tactico: $perfilTacticoDetallado",
            style = MaterialTheme.typography.titleMedium
        )
        // Chips de superpoderes

        // Resumen tactico
        Text(
            text = hero.descripcion,
            style = MaterialTheme.typography.bodyMedium
        )

        //Aca lo que quize hacer es basicamente integrar el nemesis en el detalle de la card para hacer mas interactivo y no tener tan
        //desorganizada la lista, ademas para usar esta parte de la estructura heroInfoDetail que en el ejercicio guiado estaba vacio

        if(hero.nemesis != null){ //Si el heroe tiene nemesis entonces:

            Text(
                text = "⚡ Némesis Principal",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier.padding(top = 8.dp)
            )

            //Aca reutilizo la estructura del heroe para el villano, manteniendo simple y legible las responsabilidades
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(){
                    HeroImage(
                        imageUrl = hero.nemesis.imagenUrl,
                        hero.nemesis.descripcion,
                        modifier = Modifier.height(150.dp)
                    )
                }
                Column(
                    Modifier.padding(12.dp)) {
                    Text(
                        text = hero.nemesis.nombre,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = hero.nemesis.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                }
            }

        }
    }
}

private fun heroProfile(superPoderes: List<String>): String = when {
    superPoderes.any { it.contains("Inteligencia", ignoreCase = true) } &&
            superPoderes.any { it.contains("Combate", ignoreCase = true) } -> "Estratega"
    superPoderes.any { it.contains("Energia", ignoreCase = true) } -> "Cosmico"
    superPoderes.any { it.contains("velocidad", ignoreCase = true) } -> "Velocista"
    else -> "Titan"
}

@Composable
fun HeroCard(
    hero: Nombres,
    onClick: (() -> Unit)? = null,
    option: Int = 0,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            )
    ) {
        HeroImage(
            imageUrl = hero.imagenUrl,
            contentDescription = hero.nombre
        )
        if (option == 0) {
            HeroInfo(hero = hero)
        } else {
            HeroInfoDetail(hero = hero)
        }
    }
}