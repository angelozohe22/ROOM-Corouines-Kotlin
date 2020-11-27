package com.angelo.bdroom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDAO {

    @Query("SELECT * FROM ProductEntity")
    fun getAllProducts(): LiveData<MutableList<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE idProduct = :id")
    fun getProduct(id:Int): ProductEntity

    @Query("SELECT * FROM ProductEntity WHERE idProduct = :id")
    fun getProductLiveData(id:Int): LiveData<ProductEntity>

    @Insert
    fun insertProduct(productEntity: ProductEntity)

    @Update
    fun updateProduct(productEntity: ProductEntity)

    @Delete
    fun deleteProduct(productEntity: ProductEntity)

}