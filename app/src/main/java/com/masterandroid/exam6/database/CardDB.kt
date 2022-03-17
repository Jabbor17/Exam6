package com.masterandroid.networkingroom.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cards")
data class CardDB(
    @PrimaryKey
    val id: String,


    val card_number: String,
    val cvv: Int,
    val date: String,
    val name: String,

    )