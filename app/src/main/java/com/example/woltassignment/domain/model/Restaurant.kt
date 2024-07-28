package com.example.woltassignment.domain.model

data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val liked: Boolean
)