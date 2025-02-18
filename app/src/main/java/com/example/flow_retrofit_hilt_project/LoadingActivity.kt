package com.example.flow_retrofit_hilt_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.flow_retrofit_hilt_project.ui.theme.Flow_retrofit_hilt_projectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewmodel:loadviewmodel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Flow_retrofit_hilt_projectTheme {
                statehandling(viewmodel)
            }
        }
    }


    @Composable
    fun statehandling(viewmodel: loadviewmodel) {

        LaunchedEffect(key1 = true) {
            viewmodel.collect_data()
        }

        val state by viewmodel._result.observeAsState()

        when(state){
            loadState.loaded -> {
                val context= this
                val intent=Intent(context,MainActivity::class.java)
                context.startActivity(intent)
                context.finish()
            }
            loadState.loading ->{
                Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator(color = Color.Red)
                }
            }
            null -> {

            }
        }

    }
}

