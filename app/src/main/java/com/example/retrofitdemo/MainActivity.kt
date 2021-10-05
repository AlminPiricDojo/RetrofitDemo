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

        // here we use the enqueue callback to make sure that we get the data before we update the Recycler View
        // enqueue gives us async functionality like coroutines, later we will replace this with coroutines
        apiInterface?.getPrices()?.enqueue(object: Callback<Prices>{
            override fun onResponse(call: Call<Prices>, response: Response<Prices>) {
                try{
                    // once we get our data, we can update the prices Array List and use it to update the Recycler View
                    prices.add(response.body()!!.eur!!.usd!!)
                    prices.add(response.body()!!.eur!!.aud!!)
                    rvAdapter.notifyDataSetChanged()
                }catch(e: Exception){
                    Log.d("MAIN", "ISSUE: $e")
                }
            }

            override fun onFailure(call: Call<Prices>, t: Throwable) {
                Log.d("MAIN", "Unable to get data")
            }

        })

        // here we use our JSON to Kotlin classes
        apiInterface?.getAll()?.enqueue(object: Callback<PriceData>{
            override fun onResponse(call: Call<PriceData>, response: Response<PriceData>) {
                prices.add(response.body()!!.eur.usd.toString())
                prices.add(response.body()!!.eur.aud.toString())
                rvAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<PriceData>, t: Throwable) {
                Log.d("MAIN", "Unable to get data")
            }

        })
    }
}