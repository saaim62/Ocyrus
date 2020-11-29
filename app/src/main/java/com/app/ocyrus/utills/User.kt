package com.app.ocyrus.utills

import androidx.databinding.BaseObservable
import androidx.databinding.Observable

/**
 * The type User.
 */
class User : BaseObservable(), Observable {

//    "id": 3,
//    "role_id": 2,
//    "first_name": "Customer",
//    "last_name": "Raj",
//    "email": "kishu11@yopmail.com",
//    "mobile": "9747856978",
//    "dob": "",
//    "photo": "",
//    "address": "",
//    "social_id": "",
//    "social_type": "None",
//    "api_token": null,
//    "verification_string": "KzLmUeHI",
//    "status": true,
//    "is_verified": false,
//    "country_id": "",
//    "name": "Customer Raj",
//    "location": "",
//    "latitude": "",
//    "longitude": "",
//    "street": "",
//    "city": "",
//    "state": "",
//    "driving_license_number": "",
//    "driving_experience": "",
//    "country_name": "",
//    "profile_photo": "",
//    "token": ""



    var id: Int = 0
    var role_id: Int = 0
    var first_name: String? = null
    var last_name: String? = null
    var email: String? = null
    var mobile: String? = null
    var photo: String? = null
    var address: String? = null
    var social_id: String? = null
    var social_type: String? = null
    var api_token: String? = null
    var verification_string: String? = null
    var status: Boolean? = true
    var is_verified: Boolean? = false
    var country_id: String? = null
    var name: String? = null
    var profile_photo: String? = null
    var token: String? = null
    var country_name: String? = null




}