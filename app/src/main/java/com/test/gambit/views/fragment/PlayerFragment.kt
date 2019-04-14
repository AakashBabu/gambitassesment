package com.test.gambit.views.fragment


import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.test.gambit.R
import com.test.gambit.utils.*
import com.test.gambit.views.activity.DashBoardActivity
import com.test.gambit.views.adapter.PlayerListRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment : Fragment() {

    lateinit var adapter: PlayerListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_player_list.layoutManager = LinearLayoutManager(context)
        adapter = PlayerListRecyclerAdapter(context!!, object : Pager {
            override fun pageEnded(i: Int, q: String) {
                getPlayerList(i, q)
            }

        })
        etd_search.setOnFocusChangeListener { view, b -> etd_search.isCursorVisible = b }
        rv_player_list.adapter = adapter
        getPlayerList(0, "")
        etd_search.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                Log.v("responce", "event.getAction() -" + event.getAction())
                Log.v("responce", "event.getAction() -" + event.keyCode)
                if (event.getAction() == KeyEvent.KEYCODE_SEARCH || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    BasicSupport(context!!).hideKeyBoard(etd_search)
                    adapter.search_query = etd_search.text.toString()
                    getPlayerList(-1, etd_search.text.toString())
                    return true
                }
                return false
            }
        })

        fab_guide.setOnClickListener {
            var views:ArrayList<View> = ArrayList<View>()
            views.add(etd_search)
            views.add(rv_player_list.getChildAt(0))
            PopupDialoge(activity!!).showHint(views,0)
        }
    }

    private fun getPlayerList(i: Int, q: String) {

        if (Connectivity(context!!).isNetworkConnected()) {

            WebClientSupport(object : ResponceHandler {
                override fun handleSuccess(obj: Any) {
                    val model = obj as PlayerModel
                    Log.v("responce", "getPlayerList"+i)
                    if(i==0 || i == -1) {
                        rv_player_list.smoothScrollToPosition(0)
                        if(model.data.size == 0)
                            no_itemss_found.visibility = View.VISIBLE
                        else
                            no_itemss_found.visibility = View.GONE

                    }
                    adapter.addPlayers(model, i == -1)
                }

                override fun handleError(t: Throwable, retry: Boolean) {
                    if(retry)
                        getPlayerList(i,q)
                }
            }, context!!).getPlayerList(if (i == -1) 0 else i, q)
        } else {
            if (i == 0) {
                val model = StorageManager(context!!).retrivePlayers()
                Log.v("responce", "getPlayerList no")
                if (model != null)
                    adapter.addPlayers(model,true)
            } else {
                Toast.makeText(context, "Unable to Refresh", Toast.LENGTH_LONG).show()
            }
        }
    }

}
