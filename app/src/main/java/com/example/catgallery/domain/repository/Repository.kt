package com.example.catgallery.domain.repository

import com.example.catgallery.domain.model.CatData

interface Repository {
    suspend fun fetchCatList(): List<CatData>?
}