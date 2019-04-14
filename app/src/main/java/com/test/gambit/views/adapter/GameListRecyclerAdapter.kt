package com.test.gambit.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.gambit.R
import com.test.gambit.utils.*
import kotlinx.android.synthetic.main.row_games.view.*
import kotlinx.android.synthetic.main.row_players.view.*

class GameListRecyclerAdapter(
    var context: Context, var pager: Pager,
    var data: ArrayList<Games> = ArrayList<Games>(),var next_page:Int =0,var page_ended:Boolean =false,var search_query:String =""
) : RecyclerView.Adapter<GameListRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_games, null))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_home_team.text = data.get(position).home_team.full_name
        holder.txt_guest_team.text = data.get(position).visitor_team.full_name
        holder.txt_home_abb.text = data.get(position).home_team.abbreviation
        holder.txt_guest_abb.text = data.get(position).visitor_team.abbreviation
        if(position > data.size - 3 && !page_ended){
            pager.pageEnded(next_page,search_query)
        }
    }

    fun addGames(data_set: GamesModel,reset:Boolean) {
        if(reset)
            data.clear()
        data.addAll(data_set.data)
        next_page = data_set.meta.next_page
        page_ended = data_set.meta.total_pages == data_set.meta.current_page
        StorageManager(context).storeGames(GamesModel(data,data_set.meta))
        notifyDataSetChanged()
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var txt_home_team = v.txt_home_team
        var txt_guest_team = v.txt_guest_team
        var txt_home_abb = v.txt_home_abb
        var txt_guest_abb = v.txt_guest_abb
    }
}