package com.example.spacemining.interfaces

import com.example.spacemining.api.RowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RowsApi {
    @GET
    suspend fun getRowsRandom(@Url url : String) : Response<List<RowResponse>>
}