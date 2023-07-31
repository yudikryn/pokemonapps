package com.yudikryn.pokemonapps.ui.myPokemon

import com.yudikryn.pokemonapps.data.local.entity.PokemonEntity
import com.yudikryn.pokemonapps.data.remote.model.Pokemon

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        url = id.toString(),
        name = name
    )
}