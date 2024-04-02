package com.example.catgallery.data.remote

import com.example.catgallery.domain.model.CatData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("v1/images/search")
    suspend fun fetchCatList(@Query("limit") limit: Int):
            Response<List<CatData>>
}