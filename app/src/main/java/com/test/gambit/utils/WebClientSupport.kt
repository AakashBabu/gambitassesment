package com.test.gambit.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import com.test.gambit.utils.Constants.Companion.LISTCOUNT
import com.test.gambit.utils.Constants.Companion.webCall
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.HashMap

class WebClientSupport {


    val handler: ResponceHandler
    lateinit var progress: ProgressDialog
    val context: Context

    constructor(hand: ResponceHandler, context: Context) {

        handler = hand
        this.context = context
        showLoadingDialog()
    }

    fun handleResporce(obj: Any) {
        dismissLoadingDialog()
        handler.handleSuccess(obj)
    }

    fun handleError(throwable: Throwable) {
        dismissLoadingDialog(throwable)
    }

    @SuppressLint("CheckResult")
    fun getPlayerList(i :Int,q:String) {
        webCall.getPlayerList(""+i,LISTCOUNT,q).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResporce, this::handleError)
    }

    @SuppressLint("CheckResult")
    fun getGamesList(i:Int,q:String) {
        webCall.getGamesList(""+i,LISTCOUNT,q).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResporce, this::handleError)
    }


    fun showLoadingDialog() {
        progress = ProgressDialog(context)
        progress.setTitle("Loading...")
        progress.setMessage("Please wait a moment")
        progress.show()
    }

    fun dismissLoadingDialog() {
        if (progress.isShowing()) {
            progress.dismiss()
        }
    }

    private fun dismissLoadingDialog(message: Throwable) {
        if (progress.isShowing()) {
            progress.dismiss()
        }
        AlertDialog.Builder(context).setTitle("Alert")
                .setMessage(message.message)
                .setPositiveButton("Retry", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        handler.handleError(message, true)
                    }

                }).setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        handler.handleError(message, false)
                    }

                }).create().show()
    }

}