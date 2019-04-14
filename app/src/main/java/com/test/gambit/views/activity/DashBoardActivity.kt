package com.test.gambit.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.gambit.R
import com.test.gambit.views.adapter.PlayerGamesPagerAdapter
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.content_dash_board.*
import android.view.Gravity
import android.view.View



class DashBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        setupUI()

    }

    private fun setupUI() {
        viewPager.adapter = PlayerGamesPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

}
