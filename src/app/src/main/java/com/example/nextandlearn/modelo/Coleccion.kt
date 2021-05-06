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
        - completada: Indica si la colección ha sido o no completada, de tipo Boolean.
        - completado_normal: Indica si los tests normales de la colección ha sido o no completada, de tipo Boolean.
        - completado_listening: Indica si los tests listening de la colección ha sido o no completada, de tipo Boolean.
        - completado_writing: Indica si los tests writing de la colección ha sido o no completada, de tipo Boolean.
        - completado_speaking: Indica si los tests speaking de la colección ha sido o no completada, de tipo Boolean.
        - puntos_normal: Puntos conseguidos en el modo normal en esta colección.
        - puntos_listening: Puntos conseguidos en el modo listening en esta colección.
        - puntos_writing: Puntos conseguidos en el modo writing en esta colección.
        - puntos_speaking: Puntos conseguidos en el modo speaking en esta colección.


 */
@Entity(tableName = "Colecciones")
data class Coleccion(val nombre_coleccion:String,
                     val nivel:String,
                     @PrimaryKey val identificador:String,
                     val imagen:String,
                     var completada:Boolean,
                     var completado_normal:Boolean,
                     var completado_listening:Boolean,
                     var completado_writing:Boolean,
                     var completado_speaking:Boolean,
                     var puntos_normal:Int,
                     var puntos_listening:Int,
                     var puntos_writing:Int,
                     var puntos_speaking:Int
                     )
{
}