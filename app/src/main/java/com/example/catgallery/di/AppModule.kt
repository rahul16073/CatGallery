package com.example.catgallery.di

import com.example.catgallery.data.remote.CatApi
import com.example.catgallery.data.repository.RepositoryImpl
import com.example.catgallery.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCatApiInstance(): CatApi = Retrofit.Builder().
    baseUrl("https://api.thecatapi.com/").
    addConverterFactory(GsonConverterFactory.create()).
    build().create(CatApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(catApi: CatApi): Repository {
        return RepositoryImpl(catApi)
    }

}