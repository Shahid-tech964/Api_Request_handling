package com.example.flow_retrofit_hilt_project

import com.example.flow_retrofit_hilt_project.model_data.UserdataItem
import retrofit2.Response
import retrofit2.http.GET

interface api_interface {
    @GET("comments")

    suspend fun  getdata():Response<List<UserdataItem>>
}