package com.yudikryn.pokemonapps.data.remote.model

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("base_stat")
    val base_stat: String,
    @SerializedName("effort")
    val effort: String,
    @SerializedName("stat")
    val stat: Stat,
)

data class Stat(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)