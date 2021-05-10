package com.example.nextandlearn.modelo

import androidx.room.*;

/*
    Esta interfaz nos permite trabajar con los datos de nuestra tabla 'Colecciones'.
    De esta forma se han definido los métodos necesarios para poder trabajar con los datos de esta
    tabla.
 */

@Dao
interface coleccionDao {
    /*
    Este método nos permite dada una colección insertarla en la base de datos.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertaUnaColeccion(coleccion:Coleccion)

    /*
    Este método nos permite dada una lista de objetos de tipo 'Coleccion' insertarla en la base de
    datos.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertaListaColeccion(listaColecciones:MutableList<Coleccion>)

    /*
    Este método nos permite dada una Coleccion, actualizarla en la base de datos.
     */
    @Update
    fun actualizaColeccion(coleccion:Coleccion)

    /*
    Este método nos permite dada una Coleccion eliminarla de la base de datos.
     */
    @Delete
    fun eliminaColeccion(coleccion:Coleccion)

    /*
    Este método nos permite obtener todos los objetos 'Coleccion' de la base de datos
    como una lista de objetos Coleccion.
     */
    @Query("SELECT * FROM Colecciones")
    fun obtenerTodasColecciones():MutableList<Coleccion>

    /*
    Este método nos permite obtener una determinada coleccion dado su identificador
     */
    @Query("SELECT * FROM Colecciones WHERE identificador= :buscar")
    fun obtenerColeccionSegunIdentificador(buscar:String):MutableList<Coleccion>

    /*
    Este método nos permite obtener una determinada coleccion dado su identificador
     */
    @Query("SELECT * FROM Colecciones WHERE nivel= :buscar")
    fun obtenerColeccionSegunNivel(buscar:String):MutableList<Coleccion>

    /*
    Este método se encarga de obtener el número de colecciones que hay en nuestra app
     */
    @Query("SELECT count(*) FROM Colecciones")
    fun obtenerNumColecciones():Int
}
