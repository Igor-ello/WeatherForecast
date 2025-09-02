package com.obsessed.weatherforecast.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.obsessed.weatherforecast.data.WeatherModel
import com.obsessed.weatherforecast.ui.theme.BlueLight


@Composable
fun MainList(list: List<WeatherModel>, currentDay: MutableState<WeatherModel>){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        itemsIndexed(
            list
        ) { _, item ->
            ListItem(item, currentDay)
        }
    }
}


@Composable
fun ListItem(item: WeatherModel, currentDay: MutableState<WeatherModel>) { //клонирование одного элемениа
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                if (item.hours.isEmpty()) return@clickable
                currentDay.value = item
            },
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
                // если пусто, то {...}, если нет то значение currentTemp
                text = item.currentTemp.ifEmpty { "${item.minTemp}°C / ${item.maxTemp}°C" },
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


@Composable
fun DialogSearch(dialogState: MutableState<Boolean>, onSubmit: (String) -> Unit){ // Поиск
    val dialogText = remember {
        mutableStateOf("") // для обновления текаста в поиске
    }

    AlertDialog(onDismissRequest =  {
        dialogState.value = false // прячем наш диалог (нажатие не в поле диалога)
    },
        confirmButton = {
            TextButton(onClick = {
                onSubmit(dialogText.value)
                dialogState.value = false
            } ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { dialogState.value = false }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Введите название города:")
                TextField(value = dialogText.value, onValueChange = {
                    dialogText.value = it
                })
            }
        }
    )
}