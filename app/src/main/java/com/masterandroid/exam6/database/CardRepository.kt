package com.masterandroid.networkingroom.database

import android.app.Application
import com.example.datab.database.CardDao
import com.masterandroid.exam6.helper.Logger
import com.masterandroid.exam6.manager.RoomManager

class CardRepository(application: Application) {

    private val TAG: String = CardRepository::class.java.simpleName

    private val db = RoomManager.getDatabase(application)
    private val cardDao: CardDao = db!!.cardDao()

    fun getCards(): List<CardDB> {
        Logger.d(TAG, "${cardDao.getCards()}")
        return cardDao.getCards()
    }

    fun saveCard(userDB: CardDB) {
        Logger.d(TAG, "Saved")
        cardDao.saveCard(userDB)
    }

    fun deleteCards() {
        Logger.d(TAG, "Database cleared")
        cardDao.deleteAllUsers()
    }

}