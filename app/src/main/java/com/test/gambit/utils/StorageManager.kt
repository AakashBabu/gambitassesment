package com.test.gambit.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson



class StorageManager( context: Context) {
    val preferences: SharedPreferences
    val editor: SharedPreferences.Editor
    companion object {
        val KEY_USER_TOKEN: String = "key_device_token"
    }
    init {
        preferences = context.getSharedPreferences("gambit_test", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    fun getString(key:String) : String= preferences.getString(key,"")

    fun getInt(key:String) : Int = preferences.getInt(key,0)

    fun getBoolean(key:String) : Boolean = preferences.getBoolean(key,false)

    fun putString(key:String,value:String){
        editor.putString(key,value)
        editor.commit()
    }

    fun putInt(key:String,value:Int){
        editor.putInt(key,value)
        editor.commit()
    }

    fun putBoolean(key:String,value:Boolean){
        editor.putBoolean(key,value)
        editor.commit()
    }

    fun storePlayers(model : PlayerModel){
        val gson = Gson()
        val json = gson.toJson(model)
        editor.putString("players_list", json)
        editor.commit()
    }

    fun retrivePlayers():PlayerModel? = Gson().fromJson<PlayerModel>(preferences.getString("players_list", ""), PlayerModel::class.java)

    fun storeGames(model: GamesModel) {
        val gson = Gson()
        val json = gson.toJson(model)
        editor.putString("game_list", json)
        editor.commit()
    }

    fun retriveGames(): GamesModel? = Gson().fromJson<GamesModel>(preferences.getString("game_list", ""), GamesModel::class.java)

}