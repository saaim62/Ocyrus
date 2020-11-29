package com.app.ocyrus

import android.app.PictureInPictureParams
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.util.Rational
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.app.ocyrus.databinding.ActivityAuthBinding
import com.app.ocyrus.network.NetworkCall
import com.app.ocyrus.ui.home.HomeFragment
import com.app.ocyrus.ui.splash.SplashFragment
import com.app.ocyrus.base.BaseActivity
import com.app.ocyrus.base.BaseFragment
import java.util.logging.Logger

class AuthActivity : BaseActivity<ActivityAuthBinding, AuthActivityViewModel>() {

    lateinit var binding: ActivityAuthBinding

    var viewMod: AuthActivityViewModel? = null
    internal var context: Context? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this
        networkCall = NetworkCall(context)
        initView()
    }

    fun initView(){
        binding = viewDataBinding as ActivityAuthBinding

        replaceFragment(SplashFragment(intent.getBooleanExtra("isIncomingCall",false)), true, addToBackStack = false)
    }

    override val layoutId: Int
        get() =  R.layout.activity_auth

    override fun getViewModel(): AuthActivityViewModel {
        viewMod = ViewModelProvider(this).get(AuthActivityViewModel::class.java)
        return viewMod as AuthActivityViewModel
    }









}