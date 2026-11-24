package com.example.taller_rick_morty_1.View

import android.app.FragmentManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.taller_rick_morty_1.Models.RickModelCard
import com.example.taller_rick_morty_1.Models.RickModels
import com.example.taller_rick_morty_1.Models.RickModelsResponse

@Composable
fun RickMortyScreen(onPersonajeClick: (Int) -> Unit, viewModel: RickMortyViewModel = viewModel()){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when(val current = state){

        UiState.Loading -> CircularProgressIndicator()

        is UiState.Success -> LazyColumn {
                items(current.items, key = {it.id}){
                    personaje -> RickMortyCardRow(RickModelCard(
                        name = personaje.name,
                        status = personaje.status
                    ),
                    onPersonajeClick = {
                        onPersonajeClick(personaje.id)
                    }
                    )
                }


        }

        is UiState.Error -> Column{
            Text(current.message)
            Button(onClick = viewModel::cargarPersonajes) { Text("Reintentar") }
        }
    }
}

@Composable
fun RickMortyCardRow(personaje: RickModelCard,onPersonajeClick: () -> Unit ){

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(20.dp)
            .clickable(onClick = onPersonajeClick)
    ) {


        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = personaje.name, fontSize = 18.sp)
                Text(text = personaje.status, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun DetallePersonajeScreen(
    personaje: RickModels,
    onBack: () -> Unit){
    Column(modifier = Modifier.fillMaxSize()
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(personaje.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold)
        val statusColor = when(personaje.status){
            "Alive" -> Color.Green
            "Dead" -> Color.Red
            else -> Color.Gray
        }

        AsyncImage(
            model = personaje.image,
            contentDescription = "Imagen de ${personaje.name}",
            modifier = Modifier.size(240.dp).clip(RoundedCornerShape(110.dp)),
            contentScale = ContentScale.Crop
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                .size(10.dp)
                .background(
                    color = statusColor,
                    shape = CircleShape
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = personaje.status,
                color = statusColor,
                fontSize = 30.sp
            )
        }
        personaje.type?.let { Text(it, style = MaterialTheme.typography.labelLarge) }
        Text(personaje.species, style = MaterialTheme.typography.bodyMedium)
        Button(onClick = onBack) { Text("Volver")}
    }

}

@Composable
fun DetallePersonajeRoute(
    id: Int,
    onBack: () -> Unit,
    viewModel: DetalleRickViewModel = viewModel()
){
    LaunchedEffect(id) { // esto permite ejecutar la corrutina del detalle de manera segura y junto al ciclo de vida
        viewModel.cargarDetallePersonaje(id)
    }
    val state by viewModel.uiState1.collectAsStateWithLifecycle()
    when(val current = state){
            DetalleUiState.Loading -> { CircularProgressIndicator()}

            is DetalleUiState.Success -> {
                DetallePersonajeScreen(
                    personaje = current.personaje,
                    onBack = onBack
                )
            }

            is DetalleUiState.Error -> {
                Text(current.message)
            }

        }
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "lista"){
        composable("lista"){
            RickMortyScreen( onPersonajeClick = {id ->
                navController.navigate("detalle/$id") //$id es la interpolacion kotlin para reemplazarlo por el valor de id
            }
            )
        }
        composable("detalle/{id}"){ backStackEntry -> //{id} es el patron de navegacion para denotar a navigation compose que {id} es un dato dinamico
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()

            if(id != null){
                DetallePersonajeRoute(
                    id = id,
                    onBack = { navController.popBackStack()}
                )
            } else {
                Text("ID de personaje Invalido")
            }
        }
    }
}
//navegar a ruta = "detalle/$id"
//registrar una ruta = detalle/{id}
//recuperar argumento = arguments?.getString("id")?.toIntOrNull()