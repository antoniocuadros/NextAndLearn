package com.example.nextandlearn.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Colecciones")
data class Coleccion(@PrimaryKey val nombre_coleccion:String,
                     val nivel:String,
                     val identificador:String,
                     val imagen:String)
{
}