package com.test.gambit.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.gambit.R
import com.test.gambit.utils.Pager
import com.test.gambit.utils.PlayerModel
import com.test.gambit.utils.Players
import com.test.gambit.utils.StorageManager
import kotlinx.android.synthetic.main.row_players.view.*

class PlayerListRecyclerAdapter(
    var context: Context, var pager: Pager,
    var data: ArrayList<Players> = ArrayList<Players>(), var next_page : Int = 0,var page_ended:Boolean =false, var search_query : String = ""
) : RecyclerView.Adapter<PlayerListRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_players, null))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_name_first.text = data.get(position).first_name
        holder.txt_name_second.text = data.get(position).last_name
        holder.txt_team_name.text = data.get(position).team.name
        holder.txt_team_no.text = "# "+data.get(position).team.id
        holder.txt_team_position.text = "Position - "+data.get(position).position
        holder.txt_team_full_name.text = data.get(position).team.full_name
        holder.txt_team_place.text = data.get(position).team.conference+" / "+data.get(position).team.city

        if(position > data.size - 3 && !page_ended){
            pager.pageEnded(next_page,search_query)
        }
    }

    fun addPlayers(data_set: PlayerModel,reset : Boolean) {
        if(reset)
            data.clear()
        data.addAll(data_set.data)
        next_page = data_set.meta.next_page
        page_ended = data_set.meta.total_pages == data_set.meta.current_page
        StorageManager(context).storePlayers(PlayerModel(data,data_set.meta))
        notifyDataSetChanged()
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var txt_name_first = v.txt_name_first
        var txt_name_second = v.txt_name_second
        var txt_team_name = v.txt_team_name
        var txt_team_no = v.txt_team_no
        var txt_team_position = v.txt_team_position
        var txt_team_full_name = v.txt_team_full_name
        var txt_team_place = v.txt_team_place
    }

}