package com.example.nextandlearn.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
/*
    Esta clase representa un nivel de vocabulario que podemos encontrar en nuestra aplicación.
    Los atributos de la clase Vocabulario son los siguientes:
        - nombre: Nombre asociado al nivel, de tipo String.
        - imagen: Imagen asociada al nivel para ser mostrada, de tipo String.
 */
@Entity(tableName = "Niveles")
data class Nivel(@PrimaryKey val nombre:String,
                 val imagen:String)
{
}