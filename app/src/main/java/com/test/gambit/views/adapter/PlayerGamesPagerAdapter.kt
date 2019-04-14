package com.test.gambit.views.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.test.gambit.views.fragment.GamesFragment
import com.test.gambit.views.fragment.PlayerFragment

class PlayerGamesPagerAdapter( fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        if (position == 0) {
            return PlayerFragment()
        }
        return GamesFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if(position == 0) "Player" else "Games"
    }



    override fun getCount(): Int = 2


}