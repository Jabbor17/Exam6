package com.masterandroid.exam6.activity

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.masterandroid.exam6.R
import com.masterandroid.exam6.adapter.CardAdapter
import com.masterandroid.exam6.adapter.CardDBAdapter
import com.masterandroid.exam6.helper.Logger
import com.masterandroid.exam6.model.Card
import com.masterandroid.exam6.networking.RetrofitHttp
import com.masterandroid.networkingroom.database.CardDB
import com.masterandroid.networkingroom.database.CardRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//
//1- Create card list page
//2- Add credit card page
//3- Mock Api as a backend
//4- In case of Offline, use database

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var cards: ArrayList<Card>
    lateinit var cardsDB: ArrayList<CardDB>
    lateinit var b_add:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        cards = ArrayList()
        cardsDB = ArrayList()
        recyclerView = findViewById(R.id.recyclerView)
        b_add = findViewById(R.id.b_add)


        b_add.setOnClickListener {
            openAddCardActivity()
        }

        if (isInternetAvailable()) {
            apiCardListRetrofit()
            refreshAdapter(cards)

        } else {
            getUsersFromDatabase()
        }



    }

    private fun openAddCardActivity() {
        val intent = Intent(this,AddCardActivity::class.java)
        startActivity(intent)
    }

    private fun refreshAdapter(cards: java.util.ArrayList<Card>) {
        val adapter = CardAdapter(cards)
        recyclerView.adapter = adapter
    }
 private fun refreshAdapterDB(cardsDB: ArrayList<CardDB>) {
        val adapter = CardDBAdapter(cardsDB)
        recyclerView.adapter = adapter
    }

    private fun apiCardListRetrofit() {
        RetrofitHttp.apiService.listCards().enqueue(object : Callback<ArrayList<Card>> {
            override fun onResponse(
                call: Call<ArrayList<Card>>,
                response: Response<ArrayList<Card>>
            ) {

                cards.clear()
                Log.d("response", response.body().toString())
                cards.addAll(response.body()!!)
                cardsDB.clear()
                saveToDatabase(response.body()!!)
                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                refreshAdapter(cards)
                Log.d("@@@@", response.body().toString())
            }

            override fun onFailure(call: Call<ArrayList<Card>>, t: Throwable) {
                Log.d("@@@", t.message.toString())
            }

        })
    }
    private fun getUsersFromDatabase() {
        val repository = CardRepository(application)

        refreshAdapterDB(cardsDB)

        Logger.d("TAG", repository.getCards().toString())
    }


    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }
    private fun saveToDatabase(respond: ArrayList<Card>) {

        val repository = CardRepository(application)

        repository.deleteCards()

        for (i in respond) {
            val userDB = CardDB(i.id!!, i.card_number, i.cvv, i.date,i.name)
            repository.saveCard(userDB)
        }

    }
}