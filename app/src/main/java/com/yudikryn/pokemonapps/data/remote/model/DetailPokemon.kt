package com.yudikryn.pokemonapps.data.remote.model

import com.google.gson.annotations.SerializedName

data class DetailPokemon(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("abilities")
    val abilities: List<Abilities>,
    @SerializedName("sprites")
    val sprites: Sprites,
    @SerializedName("stats")
    val stats: List<Stats>,
    @SerializedName("types")
    val types: List<Types>,
)