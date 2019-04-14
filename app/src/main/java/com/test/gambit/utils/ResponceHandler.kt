package com.test.gambit.utils

interface ResponceHandler {
    public fun handleSuccess(obj:Any)
    public fun handleError(t:Throwable,retry:Boolean)
}