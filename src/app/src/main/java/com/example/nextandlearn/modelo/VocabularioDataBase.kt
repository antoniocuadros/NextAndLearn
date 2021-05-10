package com.example.nextandlearn.modelo

import android.content.Context
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.InputStream

/*
    Esta clase abstracta se encarga de definir la base de datos que contiene las tablas de las
    entidades que hemos definido para nuestro modelo. En este caso, la base de datos va a contener
    las tablas:
        - Colecciones
        - Palabras
        - Niveles
 */

@Database(entities = [Coleccion::class, Palabra::class, Nivel::class], version = 1)
abstract class VocabularioDataBase:RoomDatabase() {
    abstract val coleccionDao:coleccionDao
    abstract val palabraDao:palabraDao
    abstract val nivelDao:nivelDao

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
        if(instancia_data_base.coleccionDao.obtenerNumColecciones() == 0){
            //Leemos los datos de los JSON
            var vocabulario:MutableList<Palabra>
            var coleccion:MutableList<Coleccion>
            var niveles:MutableList<Nivel>
            var inputStream1: InputStream = context.assets!!.open("Vocabulario.json")
            var inputStream2:InputStream = context.assets!!.open("Colecciones.json")
            var inputStream3:InputStream = context.assets!!.open("Niveles.json")

            val vocabulario_texto = inputStream1.bufferedReader().use{it.readText()}
            val colecciones_texto = inputStream2.bufferedReader().use{it.readText()}
            val niveles_texto = inputStream3.bufferedReader().use{it.readText()}

            val gson = Gson()
            val tipo_a_leer1 = object : TypeToken<MutableList<Palabra>>() {}.type
            val tipo_a_leer2 = object : TypeToken<MutableList<Coleccion>>() {}.type
            val tipo_a_leer3 = object : TypeToken<MutableList<Nivel>>() {}.type

            vocabulario = gson.fromJson<MutableList<Palabra>>(vocabulario_texto, tipo_a_leer1)
            coleccion = gson.fromJson<MutableList<Coleccion>>(colecciones_texto, tipo_a_leer2)
            niveles = gson.fromJson<MutableList<Nivel>>(niveles_texto, tipo_a_leer3)


            //Insertamos los datos de ejemplo si es la primera vez que entramos
            instancia_data_base.coleccionDao.insertaListaColeccion(coleccion)
            instancia_data_base.palabraDao.insertaListaPalabras(vocabulario)
            instancia_data_base.nivelDao.insertaListaNiveles(niveles)

        }

    }

    return instancia_data_base
}