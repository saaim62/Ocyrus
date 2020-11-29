package com.app.ocyruss.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class BaseLiveData : MutableLiveData<BaseResponse<Any>>(), Observer<Any> {

    override fun onChanged(o: Any?) {

    }
}