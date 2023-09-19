package com.obsessed.weatherforecast.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obsessed.weatherforecast.R
import com.obsessed.weatherforecast.ui.theme.BlueLight

@Preview (showBackground = true)
@Composable
fun MainScreen() {
    Image(painter = painterResource(id = R.drawable.weather_bg),
        contentDescription = "image",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.6f),
        contentScale = ContentScale.FillBounds
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp),

    ) {
        Card(modifier = Modifier
            .fillMaxWidth(),
            backgroundColor = BlueLight,
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp)
        ) {

        }
    }
}