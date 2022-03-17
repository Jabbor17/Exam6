package com.example.datab.database

import androidx.room.*
import androidx.room.Dao
import com.masterandroid.exam6.model.Card
import com.masterandroid.networkingroom.database.CardDB

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCard(cardDB: CardDB)

    @Query("select * from cards")
    fun getCards():List<CardDB>

    @Query("Select * from cards where id =:id")
    fun getCard(id:Int): Card

    @Query("Delete from cards")
    fun deleteAllUsers()



}