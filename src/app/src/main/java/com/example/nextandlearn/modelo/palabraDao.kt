package com.example.nextandlearn.modelo

import androidx.room.*

/*
    La interfaz palabraDao es un DAO de la biblioteca Room que es un mecanismo que nos ofrece
    esta biblioteca para poder trabajar con los datos de una determinada tabla de nuestra base de datos.
    En este caso nos permite trabajar con los datos de nuestra tabla "Palabras".
    De esta forma se han definido los métodos necesarios para poder trabajar con los datos de esta
    tabla.
 */

@Dao
interface palabraDao {
    /*
    Este método nos permite dada una palabra insertarla en la base de datos.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertaUnaPalabra(palabra:Palabra)

    /*
    Este método nos permite dada una lista de objetos de tipo 'Palabra' insertarla en la base de
    datos.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertaListaPalabras(listaPalabras:MutableList<Palabra>)

    /*
    Este método nos permite dada una Palabra, actualizarla en la base de datos.
     */
    @Update
    fun actualizaPalabra(palabra:Palabra)

    /*
    Este método nos permite dada una Palabra eliminarla de la base de datos.
     */
    @Delete
    fun eliminaPalabra(palabra:Palabra)

    /*
    Este método nos permite obtener todos los objetos 'Palabra' de la base de datos
    como una lista de Palabras.
     */
    @Query("SELECT * FROM Palabras")
    fun obtenerTodasPalabras():MutableList<Palabra>

    /*
    Este método nos permite obtener una determinada palabra dando su traducción al español.
     */
    @Query("SELECT * FROM Palabras WHERE espanol= :buscar")
    fun obtenerPalabraSegunEspanol(buscar:String):MutableList<Palabra>

    /*
    Este método nos permite obtener una determinada palabra dando su traducción al inglés.
     */
    @Query("SELECT * FROM Palabras WHERE ingles= :buscar")
    fun obtenerPalabraSegunIngles(buscar:String):MutableList<Palabra>

    /*
    Este método nos permite obtener una determinada palabra dando su atributo 'coleccion'.
     */
    @Query("SELECT * FROM Palabras WHERE coleccion= :buscar")
    fun obtenerPalabraSegunColeccion(buscar:String):MutableList<Palabra>

    /*
    Este método nos permite obtener una determinada palabra dando su atributo 'coleccion'.
     */
    @Query("SELECT * FROM Palabras WHERE marcada= :buscar")
    fun obtenerPalabrasMarcadas(buscar:Boolean):MutableList<Palabra>

    /*
    Este método se encarga de obtener el número de palabras de una coleccion
     */
    @Query("SELECT count(*) FROM Palabras WHERE coleccion= :buscar")
    fun obtenerNumPalabrasColeccion(buscar: String):Int
}