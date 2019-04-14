package com.test.gambit.utils

import android.content.Context
import android.os.CountDownTimer
import android.widget.RelativeLayout
import android.widget.PopupWindow
import android.view.LayoutInflater
import android.view.View
import com.test.gambit.R
import kotlinx.android.synthetic.main.popup_dialoge.view.*


class PopupDialoge(val context: Context) {

    var disabel : Boolean = true
    var titles = arrayOf("1. Click here to find Players","2. Click here for more Info")
    var description = arrayOf("You can search players by Name, Position, Teams or Division. Start typing to search now","Once you FOund the Players you want to find, click on their card for more Info")

    public fun showHint(v: ArrayList<View>,i:Int){
        disabel = true
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_dialoge, null)
        val txt_next = view.txt_next
        val txt_title = view.txt_titile
        val txt_des = view.txt_des

        txt_title.text = titles[i]
        txt_des.text = description[i]

        val mypopupWindow = PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        if(i == v.size-1)
            txt_next.text = "Done"
        txt_next.setOnClickListener {
            if(!disabel) {
                mypopupWindow.dismiss()
                if(i+1 != v.size)
                    showHint(v, i + 1)
            }
        }
        mypopupWindow.setOutsideTouchable(false)
        mypopupWindow.showAsDropDown(v[i])
        startTimer()
    }

    fun startTimer(){
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                disabel = false
            }
        }.start()
    }
}