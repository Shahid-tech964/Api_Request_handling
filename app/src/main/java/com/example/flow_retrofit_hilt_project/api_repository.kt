package com.example.flow_retrofit_hilt_project

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class api_repository @Inject constructor(val apiInterface: api_interface) {

    fun emit_data()= flow{

        try {
            val data=  apiInterface.getdata()
            data.body()?.let {emit(it)  }
        }
        catch (e:Exception){
            throw e
        }



    }.flowOn(Dispatchers.IO)

}