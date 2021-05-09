package com.example.nextandlearn.fragmentos.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.nextandlearn.R
import com.example.nextandlearn.modelo.Coleccion
import com.example.nextandlearn.modelo.Palabra


/*
Esta clase representa el adaptador de la lista de colecciones de vocabulario. Este adaptador se encargará
de rellenar la vista de la lista de colecciones de vocabulario adaptando cada colección a un item según el
layout 'coleccion_item' en forma de carta e irá añadiendo a la vista dichas cartas a la vista
que contiene la lista de colecciones de vocabulario.
 */
class ColeccionAdapter(var listaColeccion:MutableList<Coleccion>, context: Context): BaseAdapter(){
    var context = context
    /*
        Este método se encargará de construir cada elemento del GridView según lo definido en el
        layout 'coleccion_item'.

        Para ello se siguen los siguientes pasos:
        -> 1) Se obtiene la vista.
        -> 2) Se obtiene de la lista de colecciones la que corresponde a la posición que se va a construir
              obteniendo la posición como un parámetro.
        -> 3) Se definen variables que referiencian los elementos (views) que forman un item (carta)
              de la lista de colecciones.
        -> 4) Se asignan los valores de la colección que va en esa posición a las vistas obtenidas en
              el paso anterior.
        -> 5) Se devuelve la vista
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // Paso 1)
        val vista: View = View.inflate(parent?.context, R.layout.coleccion_item, null)

        // Paso 2)
        val coleccionVocabulario = this.listaColeccion[position]

        //Paso 3)
        val imagen_coleccion = vista.findViewById<ImageView>(R.id.coleccion_imagen)
        val nombre_coleccion = vista.findViewById<TextView>(R.id.coleccion_nombre)
        val nivel = vista.findViewById<TextView>(R.id.coleccion_nivel)
        val carta = vista.findViewById<CardView>(R.id.carta_coleccion)

        // Paso 4)
        var imagen = coleccionVocabulario.imagen
        var id_imagen = context.resources.getIdentifier(imagen, "drawable", "com.example.nextandlearn")
        imagen_coleccion.setImageResource(id_imagen)
        nombre_coleccion.text = coleccionVocabulario.nombre_coleccion
        nivel.text = coleccionVocabulario.nivel

        //Si está completada la ponemos en verde
        if(coleccionVocabulario.completada == true){
            carta.setCardBackgroundColor(Color.parseColor("#469436"))
        }
        else{
           if(position != 0){
               if(listaColeccion[position-1].completada == false){
                   carta.setCardBackgroundColor(Color.parseColor("#454545"))
                   var id_imagen2 = context.resources.getIdentifier(imagen+"g", "drawable", "com.example.nextandlearn")
                   imagen_coleccion.setImageResource(id_imagen2)
               }
           }
        }
        //Paso 5)
        return vista
    }

    /*
    Para una determinada posición dada como argumento se devuelve
    la colección asociada a dicha posición de la lista de colecciones.
     */
    override fun getItem(position: Int): Any {
        return listaColeccion[position]
    }

    /*
    En este método, dada una posición como parámetro se devuelve un identificador,
    en este caso al ser una lista, es la misma posición.
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /*
    Este método devuelve la longitud de la lista de colecciones.
     */
    override fun getCount(): Int {
        return listaColeccion.size
    }
}