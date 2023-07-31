package com.yudikryn.pokemonapps.ui.listPokemon

import androidx.lifecycle.ViewModel
import com.yudikryn.pokemonapps.data.PokemonRepository

class ListPokemonViewModel (private val pokemonRepository: PokemonRepository) : ViewModel() {
    fun getPokemon(startFrom: Int, pageSize: Int) = pokemonRepository.getListPokemon(startFrom = startFrom, pageSize = pageSize)
}