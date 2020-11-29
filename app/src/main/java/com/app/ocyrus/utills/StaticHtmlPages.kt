package com.app.ocyruss.utills

import androidx.databinding.BaseObservable
import androidx.databinding.Observable

/**
 * The type User.
 */
class StaticHtmlPages : BaseObservable(), Observable {

    private var text: String? = null




    fun getText(): String? {
        return text
    }

    fun setText(token: String) {
        this.text = token
    }


}