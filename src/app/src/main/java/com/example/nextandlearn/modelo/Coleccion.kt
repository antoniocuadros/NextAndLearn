package com.example.nextandlearn.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
    Esta clase representa una colección de vocabulario que podemos encontrar en nuestra aplicación.
    Los atributos de la clase Vocabulario son los siguientes:
        - nombre_coleccion: Nombre de la colección, de tipo String.
        - nivel: Nivel recomendado para aprender la colección, de tipo String.
        - identificador: Identificador de la colección, de tipo String.
        - imagen: Imagen asociada a la colección, de tipo String.
 */
@Entity(tableName = "Colecciones")
data class Coleccion(val nombre_coleccion:String,
                     val nivel:String,
                     @PrimaryKey val identificador:String,
                     val imagen:String)
{
}