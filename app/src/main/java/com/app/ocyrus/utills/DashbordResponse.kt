package com.app.ocyruss.utills

import androidx.databinding.BaseObservable
import androidx.databinding.Observable

/**
 * The type User.
 */
class DashbordResponse : BaseObservable(), Observable {

//    "daily_goal": "500",
//    "per_ad_winning_price": "0.015",
//    "ad_winning_time": "30",
//    "user_winning_price": 0,
//    "today_ad_view_count": 0,
//    "advertisement": [
//    {
//        "id": 1,
//        "package_id": 3,
//        "title": "sdd",
//        "image": "",
//        "description": "sdfdsfdsfdsf",
//        "start_date": "2020-11-26",
//        "end_date": "2020-11-30",
//        "status": 1,
//        "created_at": "2020-11-13 10:10:23",
//        "updated_at": "2020-11-13 10:10:23",
//        "image_full_path": "",
//        "package": {
//        "id": 3,
//        "title": "Combo recharge",
//        "description": "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
//        "price": 50,
//        "per_day_play": 11,
//        "total_play": 10,
//        "total_week": 1
//    }
//    }


    var daily_goal : String? = null
    var per_ad_winning_price : String? = null
    var ad_winning_time : String? = null
    var user_winning_price : String? = null
    var today_ad_view_count : Int? = 0
    var advertisement : ArrayList<Advertisement>? = ArrayList()




class Advertisement : BaseObservable(), Observable {
    var id: Int? = 0

    var package_id: Int? = 0
    var title: String? = null
    var image: String? = null
    var description: String? = null
    var start_date: String? = null
    var end_date: String? = null
    var status: Int? = 0
    var created_at: String? = null
    var updated_at: String? = null
    var image_full_path: String? = null
    var package_data: Package? = null

    class Package : BaseObservable(), Observable {
        var id: Int? = 0
        var title: String? = null
        var description: String? = null
        var price: Int? = 0
        var per_day_play: Int? = 0
        var total_play: Int? = 0
        var total_week: Int? = 0


    }
}
}