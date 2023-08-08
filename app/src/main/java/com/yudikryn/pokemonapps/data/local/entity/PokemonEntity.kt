package com.yudikryn.pokemonapps.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonEntity (
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "uniqId")
    val uniqId: Int = 0,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "poke_fibo")
    var pokeFibo: Int = 0
)