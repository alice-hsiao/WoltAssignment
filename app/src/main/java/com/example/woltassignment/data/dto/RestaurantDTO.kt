package com.example.woltassignment.data.dto

data class RestaurantDTO(
    val sections: List<Section>
)

data class Section(val items: List<Item>)

data class Item(val venue: Venue, val image: Image)

data class Venue(val id: String, val name: String, val short_description: String)

data class Image(val url :String)