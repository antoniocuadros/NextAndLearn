package com.example.nextandlearn.fragmentos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nextandlearn.R
import com.example.nextandlearn.modelo.Palabra


class ListaVocabularioAdapter(var palabras:MutableList<Palabra>,context: Context, val clickListener: (Palabra, View)-> Unit): RecyclerView.Adapter<ListaVocabularioAdapter.Pager2ViewHolder>() {
    private var context = context

    inner class Pager2ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val  imagen = itemView.findViewById<ImageView>(R.id.imagen_palabra)
        val espanol = itemView.findViewById<TextView>(R.id.palabra_espanol)

        //Para poder hacer click en los elementos
        init{
            itemView.setOnClickListener{
                clickListener(palabras[adapterPosition], it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaVocabularioAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.palabra_item, parent, false))
    }

    override fun getItemCount(): Int {
        return palabras.size
    }


    override fun onBindViewHolder(holder: ListaVocabularioAdapter.Pager2ViewHolder, position: Int) {
        var identificador_imagen = context.resources.getIdentifier(palabras[position].imagen, "drawable", "com.example.nextandlearn")
        holder.imagen.setImageResource(identificador_imagen)
        holder.espanol.text = palabras[position].espanol.capitalize()
    }

}