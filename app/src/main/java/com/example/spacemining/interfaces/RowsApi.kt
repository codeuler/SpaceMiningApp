package com.example.spacemining.interfaces

import com.example.spacemining.api.RowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


/**
 * Interfaz que define las operaciones de la API para obtener filas de datos de forma aleatoria.
 */
interface RowsApi {
    /**
     * Método para obtener filas de datos de forma aleatoria desde una URL específica.
     * @param url La URL desde la cual obtener los datos.
     * @return Una respuesta [Response] que contiene una lista de objetos [RowResponse].
     */
    @GET
    suspend fun getRowsRandom(@Url url: String): Response<List<RowResponse>>
}