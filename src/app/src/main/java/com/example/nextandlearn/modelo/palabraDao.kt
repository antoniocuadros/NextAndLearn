package com.example.nextandlearn.modelo

import androidx.room.*

@Dao
interface palabraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertaUnaPalabra(palabra:Palabra)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertaListaPalabras(listaPalabras:MutableList<Palabra>)

    @Update
    fun actualizaPalabra(palabra:Palabra)

    @Delete
    fun eliminaPalabra(palabra:Palabra)

    @Query("SELECT * FROM Palabras")
    fun obtenerTodasPalabras():MutableList<Palabra>

    @Query("SELECT * FROM Palabras WHERE espanol= :buscar")
    fun obtenerPalabraSegunEspanol(buscar:String):MutableList<Palabra>

    @Query("SELECT * FROM Palabras WHERE ingles= :buscar")
    fun obtenerPalabraSegunIngles(buscar:String):MutableList<Palabra>

    @Query("SELECT * FROM Palabras WHERE coleccion= :buscar")
    fun obtenerPalabraSegunColeccion(buscar:String):MutableList<Palabra>




}