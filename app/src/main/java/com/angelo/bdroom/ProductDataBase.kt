package com.angelo.bdroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class],
    version  = 1
)
abstract class ProductDataBase: RoomDatabase(){
    abstract fun productDAO(): ProductDAO

    companion object{
        //Singleton Model
        @Volatile
        private var INSTANCE:ProductDataBase ?= null //Database instance in all application
        private const val DBNAME = "product.db"
        fun getDatabase(ctx: Context): ProductDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                //This block is protected from concurrent execution by multiple threads
                val instance = Room
                        //databaseBuilder allows the database to remain in the application at all times if we close it
                        //if we only want it to last while the app is active, we can use inMemoryDatabaseBuilder()
                    .databaseBuilder(ctx.applicationContext, ProductDataBase::class.java, DBNAME)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}