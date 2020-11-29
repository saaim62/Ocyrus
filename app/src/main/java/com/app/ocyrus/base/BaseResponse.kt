package com.app.ocyrus.base

import androidx.databinding.BaseObservable

/**
 * Created by admin on 12/30/2015.
 *
 * @param <T> the type parameter
</T> */
class BaseResponse<T> : BaseObservable() {

    var message: String? = null

    var status: Boolean = false

    var code: Int = 0

//    var api: String? = null
//    var error: String? = null

//    var status: Boolean = false

//    var code: String? = null

    var data: T? = null
}
