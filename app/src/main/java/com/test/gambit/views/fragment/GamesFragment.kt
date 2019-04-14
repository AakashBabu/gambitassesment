package com.test.gambit.views.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.test.gambit.R
import com.test.gambit.utils.*
import com.test.gambit.views.activity.DashBoardActivity
import com.test.gambit.views.adapter.GameListRecyclerAdapter
import com.test.gambit.views.adapter.PlayerListRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_games.*
import android.view.KeyEvent


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GamesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class GamesFragment : Fragment() {

    lateinit var adapter: GameListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_gamer_list.layoutManager = LinearLayoutManager(context)
        adapter = GameListRecyclerAdapter(context!!, object : Pager {
            override fun pageEnded(i: Int, q: String) {
                adapter.search_query = q
                getGameList(i, q)
            }

        })
        etd_search.setOnFocusChangeListener { view, b -> etd_search.isCursorVisible = b }
        rv_gamer_list.adapter = adapter
        getGameList(0, "")
        etd_search.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.getAction() == KeyEvent.KEYCODE_SEARCH || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    BasicSupport(context!!).hideKeyBoard(etd_search)
                    adapter.search_query = etd_search.text.toString()
                    getGameList(-1, etd_search.text.toString())
                    return true
                }
                return false
            }
        })
    }

    private fun getGameList(i: Int, q: String) {
        if (Connectivity(context!!).isNetworkConnected()) {
            WebClientSupport(object : ResponceHandler {
                override fun handleSuccess(obj: Any) {
                    val model = obj as GamesModel
                    Log.v("responce", "getPlayerList")
                    if(i==0 || i == -1) {
                        rv_gamer_list.smoothScrollToPosition(0)
                        if(model.data.size == 0)
                            no_itemss_found.visibility = View.VISIBLE
                        else
                            no_itemss_found.visibility = View.GONE
                    }
                    adapter.addGames(model, i == -1)
                }

                override fun handleError(t: Throwable, retry: Boolean) {
                    if(retry)
                        getGameList(i,q)

                }
            }, context!!).getGamesList(if (i == -1) 0 else i, q)
        } else {
            if (i == 0) {
                val model = StorageManager(context!!).retriveGames()
                if (model != null) adapter.addGames(model,true)
            } else {
                Toast.makeText(context, "Unable to Refresh", Toast.LENGTH_LONG).show()
            }
        }
    }
}
