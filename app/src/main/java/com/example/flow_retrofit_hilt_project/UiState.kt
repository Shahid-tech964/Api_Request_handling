package com.example.flow_retrofit_hilt_project

import com.example.flow_retrofit_hilt_project.model_data.UserdataItem

sealed class UiState {
     object  loading :UiState()
     data class  success (val data:List<UserdataItem>) :UiState()
     data class error(val message:String):UiState()
}