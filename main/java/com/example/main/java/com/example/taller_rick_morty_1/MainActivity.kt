package com.example.taller_rick_morty_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taller_rick_morty_1.Repository.RetrofitInstance
import com.example.taller_rick_morty_1.Repository.RickApiService
import com.example.taller_rick_morty_1.Repository.RickRepository
import com.example.taller_rick_morty_1.View.AppNavigation
import com.example.taller_rick_morty_1.View.RickMortyScreen
import com.example.taller_rick_morty_1.View.RickMortyViewModel
import com.example.taller_rick_morty_1.ui.theme.Taller_rick_morty_1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           /* val viewModel: RickMortyViewModel = viewModel()

            Button(onClick = { viewModel.CargarPersonajes()},
                modifier = Modifier.fillMaxSize()
                ) {
                Text("Recargar")

             */
            AppNavigation()
            }
        }
    }




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Taller_rick_morty_1Theme {


    }
}