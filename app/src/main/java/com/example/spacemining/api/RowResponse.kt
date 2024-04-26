package com.example.spacemining.api

import com.google.gson.annotations.SerializedName

data class RowResponse(
    @SerializedName("OBJECT_ID") val objectId: String,
    @SerializedName("APOGEE") val apogee: Double,
    @SerializedName("PERIOD") val period: Double,
    @SerializedName("PERIGEE") val perigee: Double,
    @SerializedName("INCLINATION") val inclination: Double
)