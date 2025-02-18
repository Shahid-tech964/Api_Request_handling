package com.example.flow_retrofit_hilt_project

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flow_retrofit_hilt_project.ui.theme.Flow_retrofit_hilt_projectTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewmodel: viewmodel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Flow_retrofit_hilt_projectTheme {
   Scaffold(viewmodel)
            }
        }

    }
}
//Scaffold
@Composable
fun Scaffold(viewmodel:viewmodel) {
    val snakbarstate= remember { SnackbarHostState() }
   Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
       SnackbarHost(hostState = snakbarstate)
   }) {  paddingValues ->
       Ui(viewmodel,paddingValues,snakbarstate)
   }

}



@Composable
fun Ui(viewmodel: viewmodel,paddingValues: PaddingValues,snackbarHostState: SnackbarHostState) {

    LaunchedEffect(key1 = Unit) {
        viewmodel.collect_data()
    }
    val uistate by viewmodel._result.observeAsState()

    when(uistate){
        is UiState.loading ->{

        }

        is UiState.error -> {
            val error=(uistate as UiState.error).message

            LaunchedEffect(key1 = true) {
               val result = snackbarHostState.showSnackbar(message =error,actionLabel = "Retry")
                when(result){
                    SnackbarResult.Dismissed -> {

                    }
                    SnackbarResult.ActionPerformed -> {
                        viewmodel.collect_data()
                    }
                }
            }


        }


        is UiState.success -> {
        val data =(uistate as UiState.success).data
            data.let {
                Box(modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize().padding(paddingValues))
                {
                    LazyColumn() {
                        items(it.take(200)){
                            Text(text = it.id.toString(), fontSize = 20.sp)
                        }
                    }
                }
            }
        }
        null -> {

        }
    }

}
