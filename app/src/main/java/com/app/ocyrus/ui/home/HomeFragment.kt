package com.app.ocyrus.ui.home


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.app.ocyrus.R
import com.app.ocyrus.databinding.FragmentHomeBinding
import com.app.ocyrus.utills.AppManager
import com.app.ocyrus.base.BaseActivity
import com.app.ocyrus.base.BaseFragment
import com.app.ocyrus.base.BaseNavigator
import com.app.ocyrus.base.BaseResponse
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * The type Home fragment.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>, BaseNavigator {

    internal var liveData: MutableLiveData<BaseResponse<Void>>? = null

    private var shown: Boolean? = false

    private var fromLogout = false

    internal lateinit var binding: FragmentHomeBinding

    internal var viewModel: HomeFragmentViewModel? = null

    private var totalDailyGoal : Int = 500
    private var completeTillnow : Float = 13f
    private var oneAdPrice : Float = 0.45f

    override val layoutId: Int
        get() {
            return R.layout.fragment_home
        }

    @SuppressLint("ValidFragment")
    constructor(fromLogout: Boolean) {
        // Required empty public constructor
        this.fromLogout = fromLogout
    }

    constructor() {
        // Required empty public constructor
    }

    override fun getViewModel(): HomeFragmentViewModel {
        viewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)
        return viewModel as HomeFragmentViewModel
    }

    override fun handlingBackPressed(): Boolean {
        if (fromLogout) {
            (activity as BaseActivity<*, *>).finish()
            return true
        } else
            return false

    }

    override fun onViewCreated(@NonNull view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //FacebookSdk.sdkInitialize(context)
        //FirebaseApp.initializeApp(context!!)
        binding = viewDataBinding as FragmentHomeBinding
        viewModel?.setNavigator(this)
        allWork()

    }

    private fun allWork() {

        binding.tvName.text = "Hello "+AppManager.getUser().name
        binding.tvNoOfDailyGoal.text = ""+totalDailyGoal

        binding.tvProgressCompelete.text = ""+completeTillnow+"%"
        binding.tvNoOfCompleteAdd.text = ""+(totalDailyGoal*(completeTillnow/100))

        binding.progressBar.setProgress(completeTillnow,true)
        getTotalAmount()

    }


    private  fun getTotalAmount(){
        var myPrice = (totalDailyGoal*(completeTillnow/100))*oneAdPrice

        binding.tvTotalEarn.text = String.format("%.3f",  myPrice.toDouble())
    }

    fun getP2PisOn (): Boolean{

        return tvOn.isChecked
    }
    fun showOnlyP2p (){

       binding.topView.visibility = View.GONE

    }
    companion object {
        /**
         * The constant RC_SIGN_IN.
         */
        private val RC_HOME = 11111
    }

    override fun handleError(message: String?, tag: Int, code: Int?) {
        showErrorBar(context, message)
    }
}