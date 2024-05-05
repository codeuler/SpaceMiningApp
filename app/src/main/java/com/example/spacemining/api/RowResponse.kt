package com.example.spacemining.api

import com.google.gson.annotations.SerializedName

/**
 * Clase que representa la respuesta de la API para cada fila de datos.
 * @property objectId El identificador del objeto.
 * @property apogee El apogeo del objeto.
 * @property period El período del objeto.
 * @property perigee El perigeo del objeto.
 * @property inclination La inclinación del objeto.
 */
data class RowResponse(
    @SerializedName("OBJECT_ID") val objectId: String,
    @SerializedName("APOGEE") val apogee: Double,
    @SerializedName("PERIOD") val period: Double,
    @SerializedName("PERIGEE") val perigee: Double,
    @SerializedName("INCLINATION") val inclination: Double
)
