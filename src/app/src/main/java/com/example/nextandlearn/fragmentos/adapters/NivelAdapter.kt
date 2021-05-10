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
    Este adapatador, dada una lista de objetos de tipo Nivel adaptará cada objeto a un item según lo definido en el layout 'nivel\_item' y los añadirá a la vista en forma de lista a una columna.
 */
/*
    El atributo de esta clase es:
        -> context: Atributo de tipo Context que representa el contexto del fragmento.
 */
class NivelAdapter(var listaNiveles:MutableList<Nivel>, context: Context): BaseAdapter(){
    var context = context
    /*
        Se siguen los siguientes pasos:
        -> 1) Se obtiene la vista.
        -> 2) Se obtiene de la lista de niveles la que corresponde a la posición que se va a construir
              obteniendo la posición como un parámetro.
        -> 3) Se definen variables que referencian los elementos (views) que forman un item (carta)
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
        Se devuelve el contenido de una posición del vector de items a mostrar.
     */
    override fun getItem(position: Int): Any {
        return listaNiveles[position]
    }

    /*
        Se devuelve el identificador de una posición.
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /*
        Se devuelve la longitud del vector de elementos a mostrar.
     */
    override fun getCount(): Int {
        return listaNiveles.size
    }
}