package com.example.nextandlearn.modelo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface nivelDao {
    /*
    Este método nos permite obtener todos los objetos 'Nivel' de la base de datos
    como una lista de objetos Nivel.
     */
    @Query("SELECT * FROM Niveles")
    fun obtenerTodosNiveles():MutableList<Nivel>

    /*
    Este método nos permite dada una lista de objetos de tipo 'Nivel' insertarla en la base de
    datos.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertaListaNiveles(listaNiveles:MutableList<Nivel>)
}