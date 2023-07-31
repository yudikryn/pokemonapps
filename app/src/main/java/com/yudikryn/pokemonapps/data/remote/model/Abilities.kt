package com.yudikryn.pokemonapps.data.remote.model

import com.google.gson.annotations.SerializedName

data class Abilities(
    @SerializedName("ability")
    val ability: Ability,
    @SerializedName("slot")
    val slot: String,
)

data class Ability(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)