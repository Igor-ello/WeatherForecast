package com.obsessed.weatherforecast.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.obsessed.weatherforecast.data.WeatherModel
import com.obsessed.weatherforecast.ui.theme.BlueLight

@Composable
fun ListItem(item: WeatherModel) { //клонирование одного элемениа
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp),
        backgroundColor = BlueLight,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 5.dp, start = 8.dp, bottom = 5.dp)
            ) {
                Text(text = item.time, color = Color.White)
                Text(text = item.condition, color = Color.White)
            }
            Text(
                text = item.currentTemp.ifEmpty { "${item.minTemp}°C / ${item.maxTemp}°C" }, //если пусто, то {...}, если нет то значение currentTemp
                color = Color.White,
                style = TextStyle(fontSize = 25.sp)
            )
            AsyncImage(
                model = "https:${item.icon}", //ссылка на картинку
                contentDescription = "image_forecast_2",
                modifier = Modifier
                    .size(35.dp)
                    .padding(end = 8.dp)
            )
        }
    }
}