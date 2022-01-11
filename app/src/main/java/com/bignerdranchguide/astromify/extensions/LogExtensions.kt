package com.bignerdranchguide.astromify.extensions

import android.util.Log

fun log(any: Any, message: String) {
    Log.d(any::class.simpleName, message)
}