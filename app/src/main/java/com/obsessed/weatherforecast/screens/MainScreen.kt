package com.obsessed.weatherforecast.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
import com.obsessed.weatherforecast.R
import com.obsessed.weatherforecast.data.WeatherModel
import com.obsessed.weatherforecast.ui.theme.BlueLight
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun MainCard() {
    Column(
        modifier = Modifier
            .padding(5.dp),

        ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = BlueLight,
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = "20 Jun 2022 13:51",
                        style = TextStyle(fontSize = 15.sp),
                        color = Color.White
                    )
                    AsyncImage(
                        model = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
                        contentDescription = "image_forecast",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = 8.dp)
                    )
                }
                Text(
                    text = "Madrid",
                    style = TextStyle(fontSize = 24.sp),
                    color = Color.White
                )
                Text(
                    text = "23°C",
                    style = TextStyle(fontSize = 65.sp),
                    color = Color.White
                )
                Text(
                    text = "Sunny",
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton( onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "picture_search",
                            tint = Color.White
                        )
                    }

                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = "23°C/12°C",
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White
                    )

                    IconButton( onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sync),
                            contentDescription = "picture_sync",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(){
    var tabList = listOf("HOURS", "DAYS")
    var pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage // страница, которая открыта
    val coroutineScope = rememberCoroutineScope() //для запуска анимации

    Column(modifier = Modifier
        .padding(start = 5.dp, end = 5.dp)
        .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex, // выбранный индекс
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos)
                )
            }, // подсветка выбранного элемента
            backgroundColor = BlueLight,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed{index, text -> // цикл для создания элементов (Tab)
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {// запуск на второстепенном потоке
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = text)
                    }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) {index ->
            LazyColumn(modifier = Modifier.fillMaxSize()){
                itemsIndexed(
                    listOf(
                        WeatherModel( // прогноз на этот день
                            "London",
                            "10:00",
                            "25°C",
                            "Sunny",
                            "//cdn.weatherapi.com/weather/64x64/night/143.png",
                            "",
                            "",
                            "",
                        ),
                        WeatherModel( // прогноз по дням
                            "London",
                            "26/07/2022",
                            "",
                            "Sunny",
                            "//cdn.weatherapi.com/weather/64x64/night/143.png",
                            "26°",
                            "12°",
                            "information in array",
                        ),
                    )
                ) {
                            _, item -> ListItem(item)
                }
            }
        }
    }
}