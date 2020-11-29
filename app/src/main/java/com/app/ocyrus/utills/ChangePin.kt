package com.app.ocyrus.utills

import androidx.databinding.BaseObservable
import androidx.databinding.Observable

/**
 * The type User.
 */
class ChangePin : BaseObservable(), Observable {


    private var pin: String? = null

    fun getPin(): String? {
        return pin
    }
    fun setPin(): String? {
        return pin
    }
}