package ru.aqude.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.aqude.timer.ui.theme.TimerTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    TimerMain()
                }
            }
        }
    }
}

@Composable
fun TimerMain() {
    var valueSlider by remember {
        mutableStateOf(0.0f)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxSize()) {
        TimerData(valueSlider)
        InputData(onSliderValueChanged = {value ->
            valueSlider = value
        })
    }
}

@Composable
fun TimerData(sliderValue: Float) {

    val seconds = when (sliderValue.toInt()) {
        0 -> 15
        1 -> 30
        2 -> 60
        3 -> 600
        4 -> 900
        5 -> 1800
        else -> 1
    }

    val timeOnTimer by remember {
        mutableStateOf(seconds)
    }
    var timeCopy by remember {
        mutableStateOf(timeOnTimer)
    }
    Column() {
        Text(text = timeCopy.toString(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        
        Button(onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                while (timeCopy != 0) {
                    delay(1000)
                    timeCopy--
                }
                    timeCopy = timeOnTimer
            }
        }
        ) {
            
        }
    }
    
}

@Composable
fun InputData(onSliderValueChanged: (Float) -> Unit) {
    // Состояние для значения слайдера
    var sliderValue by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp)
    ) {
        Card(backgroundColor = Color.DarkGray) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.padding(10.dp)) {
                Box(modifier = Modifier.padding(10.dp) ) {
                    Slider(
                        value = sliderValue,
                        onValueChange = {value ->
                            sliderValue = value
                            onSliderValueChanged(value)},
                        valueRange = 0f..5f, // диапазон значений от 0 до 5
                        steps = 5,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White, // цвет ползунка
                            activeTrackColor = Color.Green, // цвет активной дорожки
                            inactiveTrackColor = Color.Green.copy(alpha = 0.5f) // цвет неактивной дорожки
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = when (sliderValue.toInt()) {
                            0 -> "15 сек"
                            1 -> "30 сек"
                            2 -> "1 мин"
                            3 -> "10 мин"
                            4 -> "15 мин"
                            5 -> "30 мин"
                            else -> "error"
                        },
                        style = MaterialTheme.typography.caption,
                        color = Color.Green,
                        fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
            }

        }

    }

}

