package com.rmnivnv.cryptomoon.model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by rmnivnv on 22/07/2017.
 */
class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(CRYPTOMOON_PREFS, Context.MODE_PRIVATE)

    companion object {
        val SELECTED_COINS_KEY = "selected_coins"
        val SELECTED_COINS_DEFAULT = """{"tsyms":["USD"],"fsyms":["BTC","ETH","XRP","LTC","XMR","ETC","DASH","BNT","SNT"]}"""
    }

    fun getSelectedCoins(): HashMap<String, ArrayList<String>> {
        val type = object : TypeToken<HashMap<String, ArrayList<String>>>() {}.type
        return Gson().fromJson<HashMap<String, ArrayList<String>>>(prefs.getString(SELECTED_COINS_KEY,
                SELECTED_COINS_DEFAULT), type)
    }

    fun setSelectedCoins(coins: HashMap<String, ArrayList<String>>) {
        val editor = prefs.edit()
        editor.putString(SELECTED_COINS_KEY, Gson().toJson(coins))
        editor.apply()
    }
}