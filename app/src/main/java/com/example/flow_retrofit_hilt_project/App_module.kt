package com.example.flow_retrofit_hilt_project

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object App_module{

    @Provides
    @Singleton
    fun retrofit():api_interface{
        return Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").
        addConverterFactory(GsonConverterFactory.create()).build().create(api_interface::class.java)

    }


}