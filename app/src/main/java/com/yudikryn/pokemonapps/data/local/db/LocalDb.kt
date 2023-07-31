package com.yudikryn.pokemonapps.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yudikryn.pokemonapps.data.local.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1, exportSchema = false
)
abstract class LocalDb : RoomDatabase() {
    abstract fun localDao(): LocalDao

    companion object {

        @Volatile
        private var INSTANCE: LocalDb? = null

        fun getInstance(context: Context): LocalDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDb::class.java,
                    "pokemon_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
