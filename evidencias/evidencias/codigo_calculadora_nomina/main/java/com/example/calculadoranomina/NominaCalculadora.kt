package com.example.calculadoranomina

data class ResultadoNomina(
    val valorHora: Double,
    val totalHorasExtra: Double,
    val auxilioTransporte: Double,
    val totalDevengado: Double,
    val aporteSalud: Double,
    val aportePension: Double,
    val fondoSolidaridad: Double,
    val totalDeducciones: Double,
    val salarioNeto: Double
)
const val SALARIO_MINIMO = 1750905.0 //Decreto 1469 de 2025
private const val AUXILIO_TRANSPORTE = 249095.0 //Decreto 1470 de 2025
private const val HORAS_ORDINARIAS_MES = 210 //Jornada de 42 horas semanales (Ley 2101 de 2021), vigente desde el 15 de julio de 2026
private const val APORTE_SALUD = 0.04 // Ley 100 de 1993
private const val APORTE_PENSION = 0.04 //Ley 100 de 1993
private const val FONDO_SOLIDARIDAD = 0.01 //Ley 797 de 2003

fun calcularNomina(salarioBasico: Double,
                           horasDiurnas: Double,
                           horasNocturnas: Double,
                           esDominical: Boolean,
                           transporteEmpresa: Boolean
): ResultadoNomina
{
    val valorHora = salarioBasico / HORAS_ORDINARIAS_MES

    val factorDiurno = if(esDominical) 2.15 else 1.25
    val factorNocturno = if(esDominical) 2.65 else 1.75

    val pagoExtraDiurnas = horasDiurnas * valorHora * factorDiurno
    val pagoExtraNocturnas = horasNocturnas * valorHora * factorNocturno
    val totalHorasExtra = pagoExtraNocturnas + pagoExtraDiurnas

    val ibc = salarioBasico + totalHorasExtra

    val auxilioTransporte = if(!transporteEmpresa && salarioBasico <= (SALARIO_MINIMO * 2)){
        AUXILIO_TRANSPORTE
    }
    else{
        0.0
    }

    val totalDevengado = ibc + auxilioTransporte

    val aporteSalud = ibc * APORTE_SALUD
    val aportePension = ibc * APORTE_PENSION
    val fondoSolidaridad = if(ibc >= (SALARIO_MINIMO * 4)){
        ibc * FONDO_SOLIDARIDAD
    }
    else{
        0.0
    }
    val totalDeducciones = aporteSalud + aportePension + fondoSolidaridad

    val salarioNeto = totalDevengado - totalDeducciones

    return ResultadoNomina(
        valorHora = valorHora,
        totalHorasExtra = totalHorasExtra,
        auxilioTransporte = auxilioTransporte,
        totalDevengado = totalDevengado,
        aporteSalud = aporteSalud,
        aportePension = aportePension,
        fondoSolidaridad = fondoSolidaridad,
        totalDeducciones = totalDeducciones,
        salarioNeto = salarioNeto
    )


}

enum class RangoSalarial { RANGO_1, RANGO_2, RANGO_3 }

fun clasificarRango(salarioBasico: Double): RangoSalarial {
    return when {
        salarioBasico <= (SALARIO_MINIMO * 2) -> RangoSalarial.RANGO_1
        salarioBasico < (SALARIO_MINIMO * 4) -> RangoSalarial.RANGO_2
        else -> RangoSalarial.RANGO_3
    }
}

