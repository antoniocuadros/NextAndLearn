package com.example.nextandlearn.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Niveles")
data class Nivel(@PrimaryKey val nombre:String,
                 val imagen:String)
{
}