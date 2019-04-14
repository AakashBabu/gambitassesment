package com.test.gambit.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class BasicSupport(var context: Context) {

    public fun hideKeyBoard(view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }
}