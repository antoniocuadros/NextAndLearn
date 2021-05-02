package com.example.nextandlearn.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
    Esta clase representa una palabra del vocabulario que podemos encontrar en nuestra aplicación.
    Los atributos de la clase Vocabulario son los siguientes:
        - espanol: Palabra en español, de tipo String.
        - ingles: Misma palabra en su traducción al inglés, de tipo String.
        - imagen: Nombre de la imagen asociada a la palabra, de tipo String.
        - coleccion: Nombre de la colección de vocabulario a la que pertenece la palabra, de tipo String.
 */

@Entity(tableName = "Vocabulario")
data class Vocabulario(val espanol:String,
                       @PrimaryKey val ingles: String,
                       val imagen:String,
                       val coleccion: String)
{
}