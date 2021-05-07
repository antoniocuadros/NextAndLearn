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
import com.example.nextandlearn.modelo.Nivel

/*
Esta clase representa el adaptador de la lista de niveles. Este adaptador se encargará
de rellenar la vista de la lista de niveles adaptando cada nivel a un item según el
layout 'nivel_item' en forma de carta e irá añadiendo a la vista dichas cartas a la vista
que contiene la lista de niveles.
 */
class NivelAdapter(var listaNiveles:MutableList<Nivel>, context: Context): BaseAdapter(){
    var context = context
    /*
        Este método se encargará de construir cada elemento del GridView según lo definido en el
        layout 'coleccion_item'.

        Para ello se siguen los siguientes pasos:
        -> 1) Se obtiene la vista.
        -> 2) Se obtiene de la lista de niveles la que corresponde a la posición que se va a construir
              obteniendo la posición como un parámetro.
        -> 3) Se definen variables que referiencian los elementos (views) que forman un item (carta)
              de la lista de niveles.
        -> 4) Se asignan los valores del nivel que va en esa posición a las vistas obtenidas en
              el paso anterior.
        -> 5) Se devuelve la vista
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // Paso 1)
        val vista: View = View.inflate(parent?.context, R.layout.nivel_item, null)

        // Paso 2)
        val nivel = this.listaNiveles[position]

        //Paso 3)
        val imagen_nivel_palabra = vista.findViewById<ImageView>(R.id.imagen_nivel_palabra)
        val nombre_nivel = vista.findViewById<TextView>(R.id.nombre_nivel)


        // Paso 4)
        var imagen = nivel.imagen
        var id_imagen = context.resources.getIdentifier(imagen, "drawable", "com.example.nextandlearn")
        imagen_nivel_palabra.setImageResource(id_imagen)

        nombre_nivel.text = nivel.nombre


        //Paso 5)
        return vista
    }

    /*
    Para una determinada posición dada como argumento se devuelve
    la colección asociada a dicha posición de la lista de colecciones.
     */
    override fun getItem(position: Int): Any {
        return listaNiveles[position]
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
        return listaNiveles.size
    }
}