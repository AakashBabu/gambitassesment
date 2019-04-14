package com.test.gambit.utils

data class PlayerModel(val data:List<Players>,val meta:MetaModel)
data class MetaModel(val total_pages:Int,val current_page:Int,val next_page:Int,val per_page:Int,val total_count:Int)
data class Players(val id:Int,val first_name:String,val last_name:String,val position:String,val height_feet:Int,val height_inches:Int,val weight_pounds:Int, val team : Team)
data class Team(val id:Int, val abbreviation :String, val city :String, val conference :String, val division :String, val full_name :String, val name :String)

data class GamesModel(val data:List<Games>,val meta:MetaModel)
data class Games(val id:Int,val date: String,val home_team_score :Int, val visitor_team_score:Int,val season : Int,val period : Int, val status : String, val time : String, val postseason : Boolean, val home_team:Team, val visitor_team:Team)