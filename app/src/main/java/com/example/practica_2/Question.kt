package com.example.practica_2
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(val text:String, val options:MutableList<String>, val correctAnswerIndex:Int) {


    override fun toString () : String{
        var salida:String = ""
        var iterador : Int = 1
        salida += text
        salida += "\n"
        for (i in options)
        {
            salida += "${iterador}. "
            salida += i
            salida += "\n"
            iterador++
        }
        return salida
    }
}