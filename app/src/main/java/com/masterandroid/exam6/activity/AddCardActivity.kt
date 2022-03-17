package com.masterandroid.exam6.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.masterandroid.exam6.R
import com.masterandroid.exam6.helper.Logger
import com.masterandroid.exam6.model.Card
import com.masterandroid.exam6.networking.RetrofitHttp
import com.masterandroid.networkingroom.database.CardDB
import com.masterandroid.networkingroom.database.CardRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCardActivity : AppCompatActivity() {
    lateinit var et_cardNumber:TextInputEditText
    lateinit var et_date1:TextInputEditText
    lateinit var et_date2:TextInputEditText
    lateinit var et_cvv:TextInputEditText
    lateinit var et_name:TextInputEditText
    lateinit var b_addCard:Button
    lateinit var cards:ArrayList<Card>
    lateinit var card:Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        initViews()
    }

    private fun initViews() {
        et_cardNumber = findViewById(R.id.et_cardNumber)
        et_cvv = findViewById(R.id.edt_cvv)
        et_date1 = findViewById(R.id.edt_date1)
        et_date2 = findViewById(R.id.edt_date2)
        et_name = findViewById(R.id.et_name)
        b_addCard = findViewById(R.id.b_add_new_card)
        cards = ArrayList()



        b_addCard.setOnClickListener {
            if (et_cardNumber.text != null && et_cvv.text != null &&
                et_date1.text != null && et_date2.text != null && et_name.text != null ){
                card = Card(et_cardNumber.text.toString(),et_cvv.text.toString().toInt(),
                et_date1.text.toString()+"/"+et_date2.text.toString(),et_name.text.toString())
                addApi()
                saveToDatabase(card)
                Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()

                finish()
            }else{
                Toast.makeText(this, "Please complete all Information", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun addApi() {
        RetrofitHttp.apiService.addCard(card).enqueue(object : Callback<Card> {
            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                Logger.d("@@@", response.body().toString())
                saveToDatabase(response.body()!!)

            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                Logger.e("@@@", t.message.toString())

            }
        })
    }
    private fun saveToDatabase(respond: Card) {

        val repository = CardRepository(application)

        repository.deleteCards()


            repository.saveCard(respond as CardDB)


    }



}