package com.rhmn.tictactoex.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

class Tools {
    companion object{
        fun Context.findActivity(): Activity? = when (this) {
            is Activity -> this
            is ContextWrapper -> baseContext.findActivity()
            else -> null
        }
    }

}