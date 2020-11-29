package com.app.ocyrus


import android.app.Activity
import android.app.PictureInPictureParams
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Rational
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.app.ocyrus.databinding.ActivityPictureBinding
import com.app.ocyrus.network.NetworkCall
import com.app.ocyrus.utills.AppManager
import com.app.ocyrus.utills.Util
import com.app.ocyrus.base.BaseActivity
import com.app.ocyrus.utills.DashbordResponse
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt
import java.lang.Long.parseLong
import java.util.*
import kotlin.collections.ArrayList


class P2PActivity : BaseActivity<ActivityPictureBinding, P2PViewModel>() {

    lateinit var binding: ActivityPictureBinding

    var viewMod: P2PViewModel? = null
    internal var context: Context? = null
    private lateinit var mInterstitialAd: InterstitialAd
    private var totalDailyGoal: Int = 0
    private var completeTillnow: Float = 0f
    private var oneAdPrice: Float = 0.0f
    private var userWinningPrice: String? = null
    private var isNeedShowP2P: Boolean = false

    var data = ArrayList<DashbordResponse.Advertisement>()
    var currentPage = 0
    var isLastPage = false
    var timer: Timer? = null
    val DELAY_MS: Long = 500
    var PERIOD_MS: Long = 30000

    @RequiresApi(Build.VERSION_CODES.O)
    private val mPictureInPictureParamsBuilder = PictureInPictureParams.Builder()

    /** A [BroadcastReceiver] to receive action item events from Picture-in-Picture mode.  */
    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            ad()
            intent?.let { intent ->
                if (intent.action != ACTION_MEDIA_CONTROL) {
                    return
                }

                // This is where we are called back from Picture-in-Picture action items.
                val controlType = intent.getIntExtra(EXTRA_CONTROL_TYPE, 0)
                when (controlType) {
//                    CONTROL_TYPE_PLAY -> movie.play()
//                    CONTROL_TYPE_PAUSE -> movie.pause()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ad()
        Log.e("P2pActivity", "onCreate")
        context = this
        networkCall = NetworkCall(context)
        binding = viewDataBinding as ActivityPictureBinding
        binding.userProfile.setOnClickListener {
            isNeedShowP2P = true

            Util.showAlertBox(this@P2PActivity, "Do you want to Logout?",
                { dialog, which ->
                    dialog.cancel()
                    AppManager.setUser(null)

                    var intent = Intent(this@P2PActivity, AuthActivity::class.java)
                    startActivity(intent)
                    finish()

                }, { dialog, which ->
                    dialog.cancel()
                    isNeedShowP2P = false
                }
            )
        }
//        callDashbordApi()

//        profile_photo

        //setting data
//        imageView.setBackgroundResource(data[position]);
//        if (AppManager.getUser().profile_photo != null) {
//            Glide.with(this)
//                .setDefaultRequestOptions(
//                    RequestOptions()
//                        .placeholder(R.drawable.user)
//                        .error(R.drawable.user)
//                )
//                .load(AppManager.getUser().profile_photo).into(binding.userProfile)
//        }

        binding.tab.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                if (checkedId == R.id.tvOn) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        minimize()

                    }
                }
            }
        })


    }

    override fun onStart() {
        super.onStart()
        Log.e("P2pActivity", "onStart")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStop() {
        super.onStop()
        Log.e("P2pActivity", "onStop")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            movie.showControls()
            if (timer != null) {
                timer!!.cancel()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
    }

    fun callDashbordApi() {
        viewMod!!.getDashbordData(networkCall).observe(this, Observer {

            data = it.data
                ?.advertisement!!

            totalDailyGoal = it.data!!.daily_goal?.let { it1 -> parseInt(it1) }!!
            completeTillnow = parseFloat(it.data!!.today_ad_view_count.toString())
            oneAdPrice = parseFloat(it.data!!.per_ad_winning_price.toString())
            PERIOD_MS = parseLong((parseInt(it.data!!.ad_winning_time!!) * 1000).toString())


            it.data!!.user_winning_price?.let { it1 -> userWinningPrice = it1 }!!

            Log.e("time", "$userWinningPrice = > $PERIOD_MS")
            allWork()

            val adapter: PagerAdapter = CustomAdapter(context as Activity?, data)
            binding.viewpager.adapter = adapter
        })
    }

    override val layoutId: Int
        get() = R.layout.activity_picture

    override fun getViewModel(): P2PViewModel {
        viewMod = ViewModelProvider(this).get(P2PViewModel::class.java)
        return viewMod as P2PViewModel
    }


    companion object {

        /** Intent action for media controls from Picture-in-Picture mode.  */
        private val ACTION_MEDIA_CONTROL = "media_control"

        /** Intent extra for media controls from Picture-in-Picture mode.  */
        private val EXTRA_CONTROL_TYPE = "control_type"

        /** The request code for play action PendingIntent.  */
        private val REQUEST_PLAY = 1

        /** The request code for pause action PendingIntent.  */
        private val REQUEST_PAUSE = 2

        /** The intent extra value for play action.  */
        private val CONTROL_TYPE_PLAY = 1

        /** The intent extra value for pause action.  */
        private val CONTROL_TYPE_PAUSE = 2

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun minimize() {
//         Hide the controls in picture-in-picture mode.
//        movie.hideControls()
//         Calculate the aspect ratio of the PiP screen.
        p2p()
    }

    fun callUpdateInfoApi(item: DashbordResponse.Advertisement) {
        var map = HashMap<String, String>()
        map["ad_id"] = "" + item.id
        map["winning_price"] = "" + oneAdPrice
//        map["ad_view_count"] = if (completeTillnow == 0f ) "1" else ""+completeTillnow
        map["ad_view_count"] = "1"
//        winning_price:0.015
//        ad_view_count:1

        viewMod!!.getUpdateInfoData(networkCall, map).observe(this, Observer {


            completeTillnow = parseFloat(it.data!!.today_ad_view_count.toString())
//            oneAdPrice = parseFloat(it.data!!.per_ad_winning_price.toString())
            it.data!!.user_winning_price?.let { it1 -> userWinningPrice = it1 }!!
            completeTillnow = parseFloat(it.data!!.today_ad_view_count.toString())

            allWork()


//            val adapter: PagerAdapter = CustomAdapter(context as Activity?, data)
//            binding.viewpager.setAdapter(adapter)
        })

    }

    /**
     * Adjusts immersive full-screen flags depending on the screen orientation.
     * @param config The current [Configuration].
     */
    private fun adjustFullScreen(config: Configuration) {
        val decorView = window.decorView
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//            scroll.visibility = View.GONE
//            movie.mAdjustViewBounds = false
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            scroll.visibility = View.VISIBLE
//            movie.mAdjustViewBounds = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRestart() {
        super.onRestart()
        // Show the video controls so the video can be easily resumed.
        Log.e("P2pActivity", "onRestart")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !isInPictureInPictureMode) {
//            movie.showControls()
            binding.scrollView.visibility = View.VISIBLE
            binding.p2pView.visibility = View.GONE
            if (timer != null) {

                timer!!.cancel()

            }
            callDashbordApi()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        super.onPause()
        Log.e("P2pActivity", "onPause")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        Log.e("P2pActivity", "onResume")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !isInPictureInPictureMode) {
//            movie.showControls()

            //binding.tvOFF.isChecked = true
            binding.scrollView.visibility = View.VISIBLE
            binding.p2pView.visibility = View.GONE
            if (timer != null) {
                timer!!.cancel()

            }
            callDashbordApi()
        }
    }

    private fun setResizing() {
// Gets the layout params that will allow you to resize the layout
// Gets the layout params that will allow you to resize the layout
        val params: ViewGroup.LayoutParams = binding.outerCircleView.layoutParams
// Changes the height and width to the specified *pixels*
// Changes the height and width to the specified *pixels*
        params.height = 184
        params.width = 184
        binding.outerCircleView.layoutParams = params
    }

    private fun allWork() {

        binding.tvName.text = "Hello " + AppManager.getUser().name
        binding.tvNoOfDailyGoal.text = "" + totalDailyGoal

        binding.tvNoOfCompleteAdd.text = "" + completeTillnow
        //  binding.tvProgressCompelete.text = ""+(completeTillnow/totalDailyGoal) * 100+"%"

        getTotalAmount()

        var perce = String.format("%.2f", ((completeTillnow / totalDailyGoal) * 100))
        binding.progressBar.setProgress(parseFloat(perce), true)
        binding.tvProgressCompelete.text = "" + perce + "%"

    }

    private fun getTotalAmount() {
        binding.tvTotalEarn.text = String.format("%.3f", userWinningPrice!!.toDouble())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun p2p() {
        Log.e("P2pActivity", "entered in to P2P")
        binding.scrollView.visibility = View.GONE
        binding.p2pView.visibility = View.GONE
        mPictureInPictureParamsBuilder.setAspectRatio(Rational(1, 1))
        enterPictureInPictureMode(mPictureInPictureParamsBuilder.build())

        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }


        /*After setting the adapter use the timer */
        val handler = Handler()
        val Update = Runnable {
            Log.e("P2pActivity", "Data size => " + data.size)
            if (currentPage == data.size - 1) {
                isLastPage = true
                currentPage = 0
                callUpdateInfoApi(data.get(data.size - 1))
                binding.viewpager.setCurrentItem(currentPage, true)
            } else {
                if (data.size > 0) {
                    callUpdateInfoApi(data.get(currentPage))
                }

                currentPage = currentPage + 1
                binding.viewpager.setCurrentItem(currentPage, true)
            }

            Log.e("P2pActivity", "Current_page => $currentPage")

//            if(isLastPage){
//                isLastPage=false
//                if(data.size>0){
//                    callUpdateInfoApi(data.get(data.size-1))
//                }
//
//            }else{
//                if(data.size>0){
//                callUpdateInfoApi(data.get(currentPage))
//                }
//            }


            Log.e("P2pActivity", "Current_page before api => $currentPage")

        }

        timer = Timer() // This will create a new Thread

        timer!!.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                if (binding.p2pView.visibility == View.VISIBLE) {
                    handler.post(Update)

                }
            }
        }, PERIOD_MS, PERIOD_MS)
        ad()
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }

    fun ad() {
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        val request = AdRequest.Builder()
            .build()
        mInterstitialAd.loadAd(request)
    }
}