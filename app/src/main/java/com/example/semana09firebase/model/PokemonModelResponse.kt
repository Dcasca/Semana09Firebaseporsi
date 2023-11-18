package com.example.semana09firebase.model

data class PokemonModelResponse (
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<PokemonModel>
)