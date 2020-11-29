package com.app.ocyrus

import android.app.Application
import com.app.ocyrus.utills.AppManager

class App : Application() {
     companion object{
     }

    override fun onCreate() {
        super.onCreate()
        AppManager.setInstance(AppManager(applicationContext));

        // Logging set to help debug issues, remove before releasing your app.


    }
}