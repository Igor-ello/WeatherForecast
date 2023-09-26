package com.obsessed.weatherforecast

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.obsessed.weatherforecast.data.WeatherModel
import com.obsessed.weatherforecast.screens.DialogSearch
import com.obsessed.weatherforecast.screens.MainCard
import com.obsessed.weatherforecast.screens.TabLayout
import com.obsessed.weatherforecast.ui.theme.WeatherForecastTheme
import org.json.JSONException
import org.json.JSONObject

const val API_KEY = "d3c47e0625444650a9d133848231909"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val currentDay = remember {
                    mutableStateOf(WeatherModel(
                        "",
                        "",
                        "0.0", // укажем значения т.к. инфа с сервера ещё не пришла
                        "",
                        "",
                        "0.0",
                        "0.0",
                        "",
                    ))
                }
                val dialogState = remember { // нужно показать диалог или нет
                    mutableStateOf(false)
                }
                if(dialogState.value){ // вызываем наш поиск
                    DialogSearch(dialogState, onSubmit = {
                        getData(it, "7", this@MainActivity, daysList, currentDay)
                    })
                }

                getData("Moscow", "3", this, daysList, currentDay)
                Image(
                    painter = painterResource(id = R.drawable.weather_bg),
                    contentDescription = "image",
                    modifier = Modifier
                        .alpha(0.6f)
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard(currentDay, onClickSync = {
                        getData("Moscow", "3", this@MainActivity, daysList, currentDay)
                    }, onClickSearch = {
                        dialogState.value = true
                    })
                    TabLayout(daysList, currentDay)
                }
            }
        }
    }
}


private fun getData(city: String, days: String, context: Context,
                    daysList: MutableState<List<WeatherModel>>,
                    currentDay: MutableState<WeatherModel>){
    val url = "https://api.weatherapi.com/v1/forecast.json" +
            "?key=$API_KEY&" +
            "&q=$city" +
            "&days=$days" +
            "&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context) // очередь на отправку запроса
    val stringRequest = StringRequest( // запрос
        Request.Method.GET,
        url, //наша ссылка
        { response -> // слушатель
            val list = getWeatherByDays(response)
            currentDay.value = list[0]
            daysList.value = list
            Log.d("MyLog", "currentTemp: ${currentDay.value.currentTemp}")
        },
        { error ->
            Log.d("MyLog", "Error $error")
        }
    )
    queue.add(stringRequest)
}

private fun getWeatherByDays(response: String): List<WeatherModel>{ // возвращает список WeatherModel
    val list = ArrayList<WeatherModel>()
    try {
        if (response.isEmpty()) return listOf() // если пришёл пустой запрос

        val mainObject = JSONObject(response)
        val city = mainObject.getJSONObject("location").getString("name")
        val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")

        for (i in 0 until days.length()) {
            val item = days[i] as JSONObject
            list.add(                         // кладём все в список
                WeatherModel(
                    city,
                    item.getString("date"),
                    "",
                    item.getJSONObject("day").getJSONObject("condition").getString("text"),
                    item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                    item.getJSONObject("day").getString("maxtemp_c"),
                    item.getJSONObject("day").getString("mintemp_c"),
                    item.getJSONArray("hour").toString()
                )
            )
        }
        list[0] = list[0].copy(
            //перезаписываем эти элементы
            time = mainObject.getJSONObject("current")
                .getString("last_updated"), // т.к. на сегодняшний день есть информация, а на завтрашний нет
            currentTemp = mainObject.getJSONObject("current").getString("temp_c"),
        )
    } catch (e: JSONException) {
        e.printStackTrace()
        Log.d("MyLog", "JSONException: $e")
    }

    return list
}