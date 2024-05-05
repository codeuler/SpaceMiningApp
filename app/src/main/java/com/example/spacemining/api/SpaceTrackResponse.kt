package com.example.spacemining.api

/**
 * Clase que representa la respuesta de SpaceTrack API.
 * @property data Un mapa que contiene conceptos como claves y listas de descripciones como valores.
 * @property time El tiempo de la respuesta.
 * @property type El tipo de la respuesta.
 */
class SpaceTrackResponse(
    val data: Map<String, List<String>>, // La respuesta contiene un mapa de conceptos a listas de descripciones
    val time: String,
    val type: String
)
