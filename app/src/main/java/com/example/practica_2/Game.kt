package com.example.practica_2

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Game (contexto:Context) {

    val preguntas:List<Question> = leer(contexto)
    var preguntasCorrectas = mutableStateOf(0)
    var preguntasErradas = mutableStateOf(0)
    var round = mutableStateOf(1)
    var indiceActual:Int = 1
    var ordenDePreguntas:List<Int> = (0..14).toList().shuffled()
    fun verificarColor (pregunta:Question, respuesta:Int, preguntaActual:Int) : Color
    {
        if (preguntaActual == respuesta) {

            if (pregunta.correctAnswerIndex == respuesta) {
                return Color.Green
            } else {

                return Color.Red
            }
        }
        else{
            return Color.Cyan
        }
    }
     fun verificarPregunta (pregunta:Question, respuesta:Int) : Boolean
    {
             if (pregunta.correctAnswerIndex == respuesta) {
                return true;

             }
             else {
                 return false;

             }

    }
     fun aumentarContadores () : Int{
        indiceActual++
        return ordenDePreguntas[indiceActual]
    }
     fun restart ():Int
    {
        ordenDePreguntas = ordenDePreguntas.shuffled()
        indiceActual = 0
        round.value = 1
        preguntasCorrectas.value = 0
        preguntasErradas.value = 0
        return ordenDePreguntas[indiceActual]
    }
    private fun leer (contexto: Context) : List<Question>
    {
        val ruta : String = "kotlin_questions.json"
        val preguntas : List<Question>?

        val jsonString = contexto.assets.open(ruta).bufferedReader().use {
            it.readText()
        }
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val dataListType = Types.newParameterizedType(List::class.java, Question::class.java)
        val adapter: JsonAdapter<List<Question>> = moshi.adapter(dataListType)
        preguntas = adapter.fromJson(jsonString)
        println(preguntas?.get(0)?.toString())
        if (preguntas != null)
        {
            return preguntas
        }
        else
        {
            return listOf(Question("No", mutableListOf("N", "O"), 0))
        }
    }
}