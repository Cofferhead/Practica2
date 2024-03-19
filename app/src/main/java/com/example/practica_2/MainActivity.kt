package com.example.practica_2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.practica_2.ui.theme.Practica_2Theme
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameApp: Game = Game(contexto = applicationContext)

        setContent {
            Practica_2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    UI(applicationContext)
                }
            }
        }
    }

}

@Composable
fun UI(contexto:Context)
{
    val control:Game = Game(contexto)
    var respuestasCorrectas by remember { mutableStateOf(0) }
    var respuestasErradas by remember { mutableStateOf(0) }
    var ronda by remember { mutableStateOf(1) }
    var preguntaActual by remember { mutableStateOf(control.preguntas[control.ordenDePreguntas[control.indiceActual]]) }
    var respuestaSeleccionada by remember {mutableStateOf(5) }
    var color = Color.Cyan
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (ronda < 7) {
            Text(text = "Ronda ${ronda}")
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = preguntaActual.text)
            Spacer(modifier = Modifier.height(50.dp))
            for (i in 0..3) {
                color = control.verificarColor(preguntaActual, respuestaSeleccionada, i)
                Button(
                    onClick = {
                        respuestaSeleccionada = i;
                        ronda++;
                        if(control.verificarPregunta(preguntaActual, respuestaSeleccionada))
                        {
                            respuestasCorrectas++
                        }
                        else{
                            respuestasErradas++
                        }
                    }, modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .background(color),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Text(text = preguntaActual.options[i])
                }
                Spacer(modifier = Modifier.height(50.dp))
                Timer().schedule(2000)
                {
                    if (respuestaSeleccionada < 5) {
                        preguntaActual = control.preguntas[control.aumentarContadores()]
                    }
                    respuestaSeleccionada = 5

                }
            }
        }
        else{
            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly)
            {
                Text(text = "Correctas: \n ${respuestasCorrectas}")
                Text(text = "Equivocadas: \n ${respuestasErradas}")
            }
            //Spacer(modifier = Modifier.height(50.dp))
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(1.dp))
                Button(onClick = { preguntaActual = control.preguntas[control.restart()];
                    ronda = 1; respuestasCorrectas = 0; respuestasErradas = 0 }) {
                    Text(text = "Reiniciar")
                }
            }
        }
    }
}