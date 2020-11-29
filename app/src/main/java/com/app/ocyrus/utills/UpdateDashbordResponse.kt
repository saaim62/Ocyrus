package com.app.ocyruss.utills

import androidx.databinding.BaseObservable
import androidx.databinding.Observable

/**
 * The type User.
 */
class UpdateDashbordResponse : BaseObservable(), Observable {


//    "id": 1,
//    "first_name": "Customer",
//    "last_name": "Raj",
//    "email": "customer756@yopmail.com",
//    "mobile": "9747856978",
//    "user_winning_price": "0.0750",
//    "today_ad_view_count": "5"

    var id : Int? = 0
    var first_name : String? = null
    var last_name : String? = null
    var email : String? = null
    var mobile : String? = null
    var user_winning_price : String? = null
    var today_ad_view_count : String? = null


}