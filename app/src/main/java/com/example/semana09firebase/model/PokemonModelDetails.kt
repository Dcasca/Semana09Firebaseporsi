package com.example.semana09firebase.model

import com.google.gson.annotations.SerializedName

data class PokemonModelDetails(
    @SerializedName("name")
    val name: String,
    @SerializedName("sprites")
    val sprites: Sprites,
    @SerializedName("abilities")
    val abilities: List<AbilityModel>

)
