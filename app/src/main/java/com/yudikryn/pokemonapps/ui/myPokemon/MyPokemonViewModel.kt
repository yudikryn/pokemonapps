package com.yudikryn.pokemonapps.ui.myPokemon

import androidx.lifecycle.ViewModel
import com.yudikryn.pokemonapps.data.PokemonRepository
import com.yudikryn.pokemonapps.data.local.entity.PokemonEntity

class MyPokemonViewModel (private val pokemonRepository: PokemonRepository) : ViewModel() {
    fun getMyPokemon() = pokemonRepository.getMyPokemon()
    fun deletePokemon(id: Int) = pokemonRepository.deletePokemon(id)
    fun updatePokemon(pokemonEntity: PokemonEntity) = pokemonRepository.updatePokemon(pokemonEntity)
}