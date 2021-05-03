package com.example.nextandlearn.modelo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
    Esta clase abstracta se encarga de definir la base de datos que contiene las tablas de las
    entidades que hemos definido para nuestro modelo. En este caso, la base de datos va a contener
    las tablas:
        - Colecciones
        - Palabras
 */

@Database(entities = [Coleccion::class, Palabra::class], version = 1)
abstract class VocabularioDataBase:RoomDatabase() {
    abstract val coleccionDao:coleccionDao
    abstract val palabraDao:palabraDao

}

/*
    Esta variable representa la única instanciación posible de la base de datos.
 */
private lateinit var instancia_data_base:VocabularioDataBase

/*
    Con este método conseguimos que nuestra clase VocabularioDataBase sea singleton, es decir,
    sea posible únicamente una instanciación de la misma, con esto conseguimos que no se cree más
    de una instancia lo cual puede provocar una degradación en las prestaciones de la aplicación.
 */
public fun obtenerBaseDatos(context: Context):VocabularioDataBase{
    if(!::instancia_data_base.isInitialized){
        instancia_data_base = Room.databaseBuilder(context.applicationContext,
            VocabularioDataBase::class.java, "vocabulario_db").allowMainThreadQueries().build()
    }

    return instancia_data_base
}