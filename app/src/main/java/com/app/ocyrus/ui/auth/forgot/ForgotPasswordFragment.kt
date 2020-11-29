package com.app.ocyrus.auth.forgot


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.app.ocyrus.AppConstants
import com.app.ocyrus.R
import com.app.ocyrus.databinding.FragmentForgotPassordBinding
import com.app.ocyrus.base.BaseFragment
import com.app.ocyrus.base.BaseNavigator
import com.app.ocyrus.base.BaseResponse
import java.util.HashMap

/**
 * The type Login fragment.
 */
class ForgotPasswordFragment : BaseFragment<FragmentForgotPassordBinding, ForgotPassViewModel>,
    BaseNavigator {




    internal lateinit var binding: FragmentForgotPassordBinding

    internal var viewModel: ForgotPassViewModel? = null




    override val layoutId: Int
        get() {
            return R.layout.fragment_forgot_passord
        }

    private val isValid: Boolean
        get() {

            if (isEmpty(getString(binding.edtEmail))) {
                showErrorBar(context, AppConstants.ERROR_ENTER_VALID_EMAIL, binding.edtEmail)
                binding.edtEmail.requestFocus()
                return false
            }
            if (!isEmailValid(getString(binding.edtEmail))) {
                showErrorBar(context, AppConstants.ERROR_INVALID_EMAIL, binding.edtEmail)
                binding.edtEmail.requestFocus()
                return false
            }





            return true
        }

    @SuppressLint("ValidFragment")
    constructor(fromLogout: Boolean) {
        // Required empty public constructor
    }

    constructor() {
        // Required empty public constructor
    }

    override fun getViewModel(): ForgotPassViewModel {
        viewModel = ViewModelProvider(this).get(ForgotPassViewModel::class.java)
        return viewModel as ForgotPassViewModel
    }


    override fun onViewCreated(@NonNull view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //FacebookSdk.sdkInitialize(context)
        //FirebaseApp.initializeApp(context!!)
        binding = viewDataBinding as FragmentForgotPassordBinding
        viewModel?.setNavigator(this)
        allWork()

    }

    private fun allWork() {
        //binding.imgBack.setOnClickListener { goBack() }


        binding.btnReset.setOnClickListener {
            if(isValid)
            callForgotApi()
        }

        binding.tvCancel.setOnClickListener {
            goBack()
        }
        binding.llBack.setOnClickListener {
            goBack()
        }
    }

    private fun callForgotApi() {


        var map = HashMap<String,String>()
        var  liveData = viewModel?.getForgotPasswordLiveData(networkCall,map)
        liveData?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val baseResponse = it as BaseResponse<Void>
            try {


//                showDialog(baseResponse.message, DialogInterface.OnClickListener { dialog, which ->
//
//                })
                showSimpleDialog(baseResponse.message)

            } catch (e: Exception) {
            }
        })
    }


    private  fun showSimpleDialog(message: String?){
        val builder = AlertDialog.Builder(activity!!)
        //set title for alert dialog
        builder.setTitle(R.string.app_name)
        //set message for alert dialog
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Ok"){dialogInterface, which ->
            goBack()
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    /**
     * Block touchable.
     */
    private fun blockTouchable() {

    }

    override fun handleError(message: String?, tag: Int,  code: Int?) {
        hideKeyboard()
        showErrorBar(context, message)
        //toast(message)
    }

    companion object {
        /**
         * The constant RC_SIGN_IN.
         */
        private val RC_SIGN_IN = 435664
    }


}