package com.example.nextandlearn.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Vocabulario")
data class Vocabulario(val espanol:String,
                       @PrimaryKey val ingles: String,
                       val imagen:String)
{
}