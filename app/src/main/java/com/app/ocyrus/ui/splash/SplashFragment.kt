package com.app.ocyrus.ui.splash

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.text.format.DateUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.app.ocyrus.P2PActivity
import com.app.ocyrus.R
import com.app.ocyrus.databinding.FragmentSplashBinding
import com.app.ocyrus.utills.AppManager
import com.app.ocyruss.auth.login.LoginFragment
import com.app.ocyruss.base.BaseFragment
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : BaseFragment<Nothing, Nothing> {

    override fun getViewModel(): Any? {
        return null
    }


    internal var handler = Handler()
    internal var runnable = { navigateFurther() }
    internal var context: Context? = null
    internal var fromNotification: Boolean? = false
    var binding: FragmentSplashBinding? = null


    /**
     * Gets layout id.
     *
     * @return the layout id
     */
    override val layoutId: Int
        get() = R.layout.fragment_splash


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        viewModel.setNavigator(this);

    }
    @SuppressLint("ValidFragment")
    constructor(fromNotification: Boolean) {
        // Required empty public constructor
        this.fromNotification = fromNotification
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding
        context = getContext()
        startSplash()
        printHashKey(context!!)
//        checkDateParse(context!!)

      // getDateTimeDiffMap("2020-10-15", "2021-2-18")
    }






    /**
     * Start splash.
     */
    private fun startSplash() {
        if(getDeviceDensity().equals(("Unknown"))){
            binding!!.img.setImageResource(R.drawable.splash1)
        }
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(runnable, 2000)

    }
    /**
     * On stop.
     */
    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }

    /**
     * On detach.
     */
    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacksAndMessages(null)
    }

    /**
     * Navigate further.
     */
    fun navigateFurther() {

        if (AppManager.isUserLoggedIn()) {

              var intent = Intent(activity, P2PActivity::class.java)
           startActivity(intent)
           activity?.finish()

//            replace(HomeFragment(), animate = true, addToBackStack = false)
//            startActivity(MainActivity.createDispatcherIntent(activity!!))

//            var intent = Intent(activity, MainActivity::class.java)
//            intent.putExtra("isIncomingCall", this.fromNotification)
//           startActivity(intent)
//           activity?.finish()
        }else{
            replace(LoginFragment(), animate = true, addToBackStack = false)
        }
    }

    fun printHashKey(pContext: Context) {
        try {
            val info = pContext.packageManager.getPackageInfo(pContext.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i(TAG, "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "printHashKey()", e)
        } catch (e: Exception) {
            Log.e(TAG, "printHashKey()", e)
        }

    }
    @SuppressLint("SimpleDateFormat")
    fun checkDateParse(pContext: Context) {
        try {
            val string = "Tue 25-Oct 2020 06:27"
            val format: DateFormat = SimpleDateFormat("EEE dd-MMM yyyy HH:mm", Locale.ENGLISH)
            val date: Date = format.parse(string)
            System.out.println(date)

            val sdf =
                SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")

            val todayAsString: String = sdf.format(date)
            try {
                val mDate = sdf.parse(todayAsString)
                val timeInMilliseconds = mDate.time
                println("Date in milli :: $timeInMilliseconds")
              
                System.out.println("isToday=>"+DateUtils.isToday(timeInMilliseconds))
                System.out.println("getRelativeTimeSpanString=>"+DateUtils.getRelativeTimeSpanString(timeInMilliseconds))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "checkDateParse()", e)
        } catch (e: Exception) {
            Log.e(TAG, "checkDateParse()", e)
        }

    }

    private fun getDeviceDensity(): String? {
        val density: Int = activity!!.getResources().getDisplayMetrics().densityDpi
        return when (density) {
            DisplayMetrics.DENSITY_MEDIUM -> "MDPI"
            DisplayMetrics.DENSITY_HIGH -> "HDPI"
            DisplayMetrics.DENSITY_LOW -> "LDPI"
            DisplayMetrics.DENSITY_XHIGH -> "XHDPI"
            DisplayMetrics.DENSITY_TV -> "TV"
            DisplayMetrics.DENSITY_XXHIGH -> "XXHDPI"
            DisplayMetrics.DENSITY_XXXHIGH -> "XXXHDPI"
            else ->
                "Unknown"
        }
    }
}
