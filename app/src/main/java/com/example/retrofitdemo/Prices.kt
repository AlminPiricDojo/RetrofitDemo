package com.example.retrofitdemo

class Prices{
    // this allows us to access the nested JSON object 'eur' to get the price data
    var eur: Currency? = null

    class Currency{
        // here we get individual price data
        // we don't need serializers
        var usd: String? = null

        var aud: String? = null
    }
}