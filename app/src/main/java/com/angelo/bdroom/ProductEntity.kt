package com.angelo.bdroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity (
    val name        : String,
    val price       : Double,
    val description : String,
    val image       : Int,
    @PrimaryKey(autoGenerate = true)
    var idProduct   : Int = 0
)