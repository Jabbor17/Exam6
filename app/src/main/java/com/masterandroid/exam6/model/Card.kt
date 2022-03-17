package com.masterandroid.exam6.model

data class Card(
    val card_number: String,
    val cvv: Int,
    val date: String,
    val name: String,
    val id: String? = null
)