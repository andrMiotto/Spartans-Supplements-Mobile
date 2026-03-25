package com.example.spartans_supplements_sobile.data

data class CartItem(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String
)