package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    private lateinit var prices: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prices = arrayListOf()

        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(prices)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.getAll()?.enqueue(object: Callback<PriceData>{
            override fun onResponse(call: Call<PriceData>, response: Response<PriceData>) {
                prices.add("AUD: ${response.body()!!.eur.aud}")
                prices.add("JPY: ${response.body()!!.eur.jpy}")
                prices.add("USD: ${response.body()!!.eur.usd}")
                rvAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<PriceData>, t: Throwable) {
                Log.d("MAIN", "Unable to get data")
            }

        })
    }
}