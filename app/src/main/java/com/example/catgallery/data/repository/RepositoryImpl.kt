package com.example.catgallery.data.repository

import com.example.catgallery.data.remote.CatApi
import com.example.catgallery.domain.model.CatData
import com.example.catgallery.domain.repository.Repository

class RepositoryImpl(private val catApi: CatApi): Repository {
    override suspend fun fetchCatList(): List<CatData>? {
        val result =  catApi.fetchCatList(10)
        if(result.isSuccessful)
            return result.body()
        return null
    }
}