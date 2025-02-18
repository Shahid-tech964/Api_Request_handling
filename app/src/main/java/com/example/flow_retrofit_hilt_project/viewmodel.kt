package com.example.flow_retrofit_hilt_project

import android.net.http.HttpException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow_retrofit_hilt_project.model_data.UserdataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class viewmodel @Inject constructor(val apiRepository: api_repository):ViewModel() {

    val _mutableResult=MutableLiveData<UiState>()
    val _result:LiveData<UiState> =_mutableResult
//collecting data from flow


    fun collect_data() {

        viewModelScope.launch {


            apiRepository.emit_data().onStart {
                _mutableResult.value = UiState.loading
            }.catch { e ->
                when (e) {
                    is IOException -> {
                        _mutableResult.value = UiState.error("please check your internet ")
                    }

                    else -> {
                        UiState.error("something went wrong ")
                    }
                }
            }
                .collect { it ->
                    _mutableResult.value = UiState.success(data = it)
                }


        }
    }


}

@HiltViewModel
// loading state
class loadviewmodel @Inject constructor(val apiInterface: api_interface):ViewModel() {

    val _mutableResult=MutableLiveData<loadState>()
    val _result:LiveData<loadState> =_mutableResult
//collecting data from flow


    fun collect_data() {

        viewModelScope.launch {
       _mutableResult.value=loadState.loading

            try {
                val data =apiInterface.getdata()

                if (data.isSuccessful){
                    _mutableResult.value=loadState.loaded
                }
            }
            catch (e:Exception){
                Log.d("Exception ", "fail to load  ")
            }





        }
    }


}