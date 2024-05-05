package com.example.spacemining.interfaces

import com.example.spacemining.api.SpaceTrackResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interfaz que define las operaciones de la API para obtener datos de SpaceTrack.
 */
interface ApiService {
    /**
     * Método para obtener datos de SpaceTrack desde la API.
     * @return Una respuesta [Response] que contiene un objeto [SpaceTrackResponse].
     */
    @GET("/data/conceptos/space_track")
    suspend fun getSpaceTrackData(): Response<SpaceTrackResponse>
}
