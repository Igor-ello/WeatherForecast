package com.obsessed.weatherforecast

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.obsessed.weatherforecast.screens.MainScreen
import com.obsessed.weatherforecast.ui.theme.WeatherForecastTheme
import org.json.JSONObject

const val API_KEY = "d3c47e0625444650a9d133848231909"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, context: Context) {
    val state = remember {
        mutableStateOf("Unknown")
    }
    val humidity = remember {
        mutableStateOf("Unknown")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Temp in $name = ${state.value} °C, humidity ${humidity.value} %")
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                onClick = {
                    getResult(name, state, humidity, context)
                },
            ) {
                Text(text = "Refresh")
            }
        }
    }
}

private fun getResult(city: String, state: MutableState<String>, humidity: MutableState<String>, context: Context){
    val url = "https://api.weatherapi.com/v1/current.json" +
            "?key=$API_KEY&" +
            "q=$city" +
            "&aqi=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
            response ->
            val obj = JSONObject(response)
            state.value = obj.getJSONObject("current").getString("temp_c")
            humidity.value = obj.getJSONObject("current").getString("humidity")
        },
        {
            error ->
            Log.d("MyLog", "Error $error")
        }
    )
    queue.add(stringRequest)
}