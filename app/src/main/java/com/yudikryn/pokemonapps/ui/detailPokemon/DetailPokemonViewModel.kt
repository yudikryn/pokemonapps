package com.yudikryn.pokemonapps.ui.detailPokemon

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yudikryn.pokemonapps.data.PokemonRepository
import com.yudikryn.pokemonapps.data.local.entity.PokemonEntity

class DetailPokemonViewModel (private val pokemonRepository: PokemonRepository) : ViewModel() {
    fun getDetailPokemon(id: Int) = pokemonRepository.getDetailPokemon(id = id)
    fun insertPokemon(pokemonEntity: PokemonEntity) = pokemonRepository.insertPokemon(pokemonEntity = pokemonEntity)
}