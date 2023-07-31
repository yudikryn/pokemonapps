package com.yudikryn.pokemonapps.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yudikryn.pokemonapps.data.local.entity.PokemonEntity

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemonEntity: PokemonEntity): Long

    @Update()
    fun updatePokemon(pokemonEntity: PokemonEntity): Int

    @Query("select * from pokemon_table")
    fun getMyPokemon(): List<PokemonEntity>

    @Query("Delete FROM pokemon_table where uniqId LIKE :id")
    fun deletePokemonById(id: Int): Int
}
