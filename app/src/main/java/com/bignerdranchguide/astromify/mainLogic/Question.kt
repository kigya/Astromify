package com.bignerdranchguide.astromify.mainLogic

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean)