package com.example.practica2
import com.example.practica_2.Question
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

class QuestionDataBase(ruta: String) {

    val preguntas:List<Question>?
    init {
        val jsonFile = File(ruta)
        val jsonString = jsonFile.readText()
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val dataListType = Types.newParameterizedType(List::class.java, Question::class.java)
        val adapter: JsonAdapter<List<Question>> = moshi.adapter(dataListType)
        preguntas = adapter.fromJson(jsonString)
    }
    fun devolverListaPreguntas () : List<Question>
    {
        if (preguntas != null)
        {
            return preguntas
        }
        else{
            return listOf(Question("First", mutableListOf(), 0))
        }
    }
    fun devolverPregunta (index:Int): Question{
        if (preguntas != null && index < preguntas.size )
        {
            return preguntas[index]
        }
        else
        {
            return Question("First", mutableListOf(), 0)
        }
    }
}