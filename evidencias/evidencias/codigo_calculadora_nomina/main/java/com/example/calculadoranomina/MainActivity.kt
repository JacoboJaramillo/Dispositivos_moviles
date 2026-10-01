package com.example.calculadoranomina

import android.R.attr.onClick
import android.os.Bundle
import android.util.Log
import androidx.compose.material3.Button
import android.widget.Switch
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadoranomina.ui.theme.CalculadoraNominaTheme
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraNominaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    calculadoraLayout()
                }

                }
            }
        }
    }


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun calculadoraLayout() {
    //inputs para los valores de salario
    var salarioInput by remember { mutableStateOf("") }
    var horasDiurnasInput by remember { mutableStateOf("") }
    var horasNocturnasInput by remember { mutableStateOf("") }
    val TAG = "prueba"
    //operador elvis
    val salario = salarioInput.toDoubleOrNull()
    val diurnas = horasDiurnasInput.toDoubleOrNull() ?: 0.0
    val nocturas = horasNocturnasInput.toDoubleOrNull() ?: 0.0

    //valores del switch
    var esDominical by remember { mutableStateOf(false) }
    var transporteEmpresa by remember { mutableStateOf(false) }

    //valores para control de errores
    var resultado by remember { mutableStateOf<ResultadoNomina?>(null) }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        )
    {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start )
        )
        campoNumerico(
            etiqueta = R.string.salario_basico,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            valor = salarioInput,
            onValueChange = {salarioInput = it},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(25.dp))
        campoNumerico(
            etiqueta = R.string.Diurnas,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            valor = horasDiurnasInput,
            onValueChange = {horasDiurnasInput = it},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(25.dp))
        campoNumerico(
            etiqueta = R.string.Nocturnas,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            valor = horasNocturnasInput,
            onValueChange = {horasNocturnasInput = it},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(50.dp))

        FilaSwitch(
            etiqueta = R.string.Dominical,
            checkeado = esDominical,
            onCheckedChange = { esDominical = it},
            modifier = Modifier
        )
        FilaSwitch(
            etiqueta = R.string.Transporte,
            checkeado = transporteEmpresa,
            onCheckedChange = { transporteEmpresa = it},
            modifier = Modifier
        )

        Button(
            onClick = {
                when {
                    salario == null -> {
                        Log.d(TAG,  "Ingrese salario basico valido")
                        resultado = null
                    }
                    salario < SALARIO_MINIMO -> {
                        mensajeError = "El salario basico no puede ser inferior al salario minimo"
                    }
                    diurnas < 0 || nocturas < 0 -> {
                        mensajeError = "Las horas extra no pueden ser negativas"
                        resultado = null
                    }
                    (diurnas + nocturas) > 48.0 -> {
                        mensajeError = "El total de horas extra no puede superar las 48 horas al mes"
                        resultado = null
                    }
                    else ->{
                        mensajeError = null
                        resultado = calcularNomina(
                            salarioBasico = salario,
                            horasDiurnas = diurnas,
                            horasNocturnas = nocturas,
                            esDominical = esDominical,
                            transporteEmpresa = transporteEmpresa
                        )

                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = stringResource(R.string.btn_calcular))
        }

    }
    resultado?.let { res ->
        val rango = clasificarRango(salarioInput.toDoubleOrNull() ?: 0.0)

        val imagenRes = when (rango) {
            RangoSalarial.RANGO_1 -> R.drawable.triste
            RangoSalarial.RANGO_2 -> R.drawable.neutro
            RangoSalarial.RANGO_3 -> R.drawable.feliz
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = imagenRes),
                    contentDescription = "Rango salarial",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 8.dp)
                )


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Valor hora ordinaria: ${formatearMoneda(res.valorHora)}")
                    Text(text = "Total Horas Extra: ${formatearMoneda(res.totalHorasExtra)}")
                    Text(text = "Auxilio Transporte: ${formatearMoneda(res.auxilioTransporte)}")
                    Text(text = "Total Devengado: ${formatearMoneda(res.totalDevengado)}")
                    Text(text = "Salud (4%): ${formatearMoneda(res.aporteSalud)}")
                    Text(text = "Pensión (4%): ${formatearMoneda(res.aportePension)}")
                    Text(text = "Fondo Solidaridad: ${formatearMoneda(res.fondoSolidaridad)}")
                    Text(text = "Total Deducciones: ${formatearMoneda(res.totalDeducciones)}")

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "Salario Neto: ${formatearMoneda(res.salarioNeto)}",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                OutlinedButton(
                    onClick = {
                        salarioInput = ""
                        horasDiurnasInput = ""
                        horasNocturnasInput = ""
                        esDominical = false
                        transporteEmpresa = false
                        mensajeError = null
                        resultado = null
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Limpiar")
                }
            }
        }
    }

}
@Composable
fun campoNumerico(
    @StringRes etiqueta: Int,
    valor: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier
)
{
    OutlinedTextField(
        value = valor,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChange,
        label = {Text(stringResource(etiqueta))} ,
        keyboardOptions = keyboardOptions

    )
}

@Composable
fun FilaSwitch(
    @StringRes etiqueta: Int,
    checkeado: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier
)
{
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(etiqueta))
        Switch(
            checked = checkeado,
            onCheckedChange = onCheckedChange
        )
    }
}

fun formatearMoneda(valor: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    format.maximumFractionDigits = 0
    return format.format(valor)
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraNominaTheme {
        calculadoraLayout()
    }
}