package com.example.flow_retrofit_hilt_project

sealed class loadState() {
    data object loading:loadState()
    data object loaded :loadState()
}