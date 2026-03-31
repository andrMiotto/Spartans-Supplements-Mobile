package com.example.spartans_supplements_sobile.data

data class CartItem(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int = 1,
    val imageUrl: String,
    val caloria: Double,
    val proteina: Double,
    val carboidratos: Double,
)