package com.app.ocyruss.utills

import androidx.databinding.BaseObservable
import androidx.databinding.Observable

/**
 * The type User.
 */
class MeetingItem : BaseObservable(), Observable {
//    {"u":"User1274","affID":"m2r9pi","PIN":1684,"type":"incoming","time":"Tue 06- Oct 01:09","duration":"2m 29s"}

     var u: String? = null
     var affID: String? = null
     var PIN: Int? = null
     var type: String? = null
     var time: String? = null
     var duration: String? = null
     var callDay: String? = null  // today , yesterday ,older






}