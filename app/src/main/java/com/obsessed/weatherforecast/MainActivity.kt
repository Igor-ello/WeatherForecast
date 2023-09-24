package com.obsessed.weatherforecast

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.obsessed.weatherforecast.screens.MainCard
import com.obsessed.weatherforecast.screens.TabLayout
import com.obsessed.weatherforecast.ui.theme.WeatherForecastTheme
import org.json.JSONObject

const val API_KEY = "d3c47e0625444650a9d133848231909"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                getData("London", "3", this)
                Image(
                    painter = painterResource(id = R.drawable.weather_bg),
                    contentDescription = "image",
                    modifier = Modifier
                        .alpha(0.6f)
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard()
                    TabLayout()
                }
            }
        }
    }
}


private fun getData(city: String, days: String, context: Context){
    val url = "https://api.weatherapi.com/v1/current.json" +
            "?key=$API_KEY&" +
            "&q=$city" +
            "&days=$days" +
            "&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context) // очередь на отправку запроса
    val stringRequest = StringRequest( // запрос
        Request.Method.GET,
        url, //наша ссылка
        { response -> // слушатель
            val obj = JSONObject(response)
            //state.value = obj.getJSONObject("current").getString("temp_c")
            //humidity.value = obj.getJSONObject("current").getString("humidity")
        },
        { error ->
            Log.d("MyLog", "Error $error")
        }
    )
    queue.add(stringRequest)
}