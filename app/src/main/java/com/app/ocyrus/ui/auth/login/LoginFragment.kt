package com.app.ocyruss.auth.login


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.app.ocyrus.AppConstants
import com.app.ocyrus.P2PActivity
import com.app.ocyrus.R
import com.app.ocyrus.databinding.FragmentLoginBinding
import com.app.ocyrus.utills.AppManager
import com.app.ocyrus.utills.SharedPref
import com.app.ocyruss.auth.signup.SignupFragment
import com.app.ocyruss.base.BaseActivity
import com.app.ocyruss.base.BaseFragment
import com.app.ocyruss.base.BaseNavigator
import com.app.ocyruss.base.BaseResponse
import com.app.ocyruss.utills.User
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.*

/**
 * The type Login fragment.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>, BaseNavigator {

    internal var liveData: MutableLiveData<BaseResponse<User>>? = null

    private var shown: Boolean? = false

    private var fromLogout = false
    private var verifyEmail = ""

    internal lateinit var binding: FragmentLoginBinding

    internal var viewModel: LoginViewModel? = null
    private lateinit var forgotPass: BottomSheetBehavior<LinearLayout>
    private lateinit var forgotOtp: BottomSheetBehavior<LinearLayout>
    private lateinit var forgotReset: BottomSheetBehavior<LinearLayout>

    override val layoutId: Int
        get() {
            return R.layout.fragment_login
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

            if (isEmpty(getString(binding.edtPassword))) {
                showErrorBar(context, AppConstants.ERROR_ENTER_PASSWORD, binding.edtPassword)
                binding.edtPassword.requestFocus()
                return false
            }




            return true
        }

    @SuppressLint("ValidFragment")
    constructor(fromLogout: Boolean) {
        // Required empty public constructor
        this.fromLogout = fromLogout
    }

    constructor() {
        // Required empty public constructor
    }

    override fun getViewModel(): LoginViewModel {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return viewModel as LoginViewModel
    }

    override fun handlingBackPressed(): Boolean {

        if (forgotReset.state == BottomSheetBehavior.STATE_EXPANDED) {
            forgotReset.state = BottomSheetBehavior.STATE_COLLAPSED
            return true
        } else if (forgotOtp.state == BottomSheetBehavior.STATE_EXPANDED) {
            forgotOtp.state = BottomSheetBehavior.STATE_COLLAPSED
            return true
        }else if (forgotPass.state == BottomSheetBehavior.STATE_EXPANDED) {
            forgotPass.state = BottomSheetBehavior.STATE_COLLAPSED
            return true
        }
        else if (fromLogout) {
            (activity as BaseActivity<*, *>).finish()
            return true
        } else
            return false

    }

    override fun onViewCreated(@NonNull view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //FacebookSdk.sdkInitialize(context)
        //FirebaseApp.initializeApp(context!!)

        binding = viewDataBinding as FragmentLoginBinding
        viewModel?.setNavigator(this)


        allWork()

    }
    private fun hideShowPassword() {
        val start: Int
        val end: Int
        if (shown!!) {
            shown = false
            start = binding.edtPassword.selectionStart
            end = binding.edtPassword.selectionEnd
            binding.edtPassword.transformationMethod = PasswordTransformationMethod()
            binding.edtPassword.setSelection(start, end)
            binding.passwordView.setImageResource(R.drawable.ic_hide_password)


        } else {
            shown = true
            start = binding.edtPassword.selectionStart
            end = binding.edtPassword.selectionEnd
            binding.edtPassword.transformationMethod = null
            binding.edtPassword.setSelection(start, end)
            binding.passwordView.setImageResource(R.drawable.ic_show_password)
        }
    }
    private fun allWork() {
        //binding.imgBack.setOnClickListener { goBack() }
      //  binding.tvSignUp.setOnClickListener { replace(SignupFragment(), true, true) }
//        createSpanForSignUp()
        loginWork()
//        forgotPasswordWork()
        SignupWork()
        binding.passwordView.setOnClickListener {
            hideShowPassword()
        }
        openForgotPasswordDialog()
        openOtpDialog()
        openResetDialog()
    }

    private fun openForgotPasswordDialog(){
//        bottomSheetReset
        val bottomSheetForgotPassword = binding.fPass.bottomSheetForgotPassword as LinearLayout
        forgotPass =  BottomSheetBehavior.from(bottomSheetForgotPassword)
        forgotPass.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {

                    }
                }
            }
        })

        binding.tvForgotPass.setOnClickListener({ view ->
//            replace(ForgotPasswordFragment(),true,true)
//            openClosebottomSheet();

            if (forgotPass.state != BottomSheetBehavior.STATE_EXPANDED) {
                forgotPass.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                forgotPass.state = BottomSheetBehavior.STATE_COLLAPSED;
            }

        })

        binding.fPass.bottomSheetForgotPassword.setOnClickListener({view ->

            forgotPass.state = BottomSheetBehavior.STATE_COLLAPSED;
        })
        binding.fPass.btnNext.setOnClickListener({
                view ->
            callForgotApi()
        })

    }
    private fun openOtpDialog(){
//        bottomSheetReset
        val bottomSheetForgotOtp = binding.fotp.bottomSheetOtp as LinearLayout
        forgotOtp =  BottomSheetBehavior.from(bottomSheetForgotOtp)
        forgotOtp.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {

                    }
                }
            }
        })

//        binding.fPass.btnNext.setOnClickListener({ view ->
////            replace(ForgotPasswordFragment(),true,true)
////            openClosebottomSheet();
////            forgotPass.state = BottomSheetBehavior.STATE_COLLAPSED;
//            if (forgotOtp.state != BottomSheetBehavior.STATE_EXPANDED) {
//                forgotOtp.state = BottomSheetBehavior.STATE_EXPANDED
//            }
//
//        })
        binding.fotp.btnNext.setOnClickListener({ view ->

            callVerifyOtpApi(verifyEmail)

        })


    }
    private fun openResetDialog(){
//        bottomSheetReset
        val bottomSheetForgotOtp = binding.fReset.bottomSheetResetPassword as LinearLayout
        forgotReset =  BottomSheetBehavior.from(bottomSheetForgotOtp)
        forgotReset.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {

                    }
                }
            }
        })

        binding.fReset.passwordView.setOnClickListener {
            hideShowResetPassword()
        }
        binding.fReset.confPasswordView.setOnClickListener {
            hideShowConfrmPassword()
        }
        binding.fReset.btnDone.setOnClickListener({ view ->

            callResetPasswordApi(verifyEmail)

        })



    }
    private fun loginWork() {
        binding.btnLogin.setOnClickListener {
//           var intent = Intent(activity, HomeActivity::class.java)
//            startActivity(intent)
//            activity?.finish()

            if (isValid)
//            //LoginAsynctask().execute()
                callLoginApi()
        }

//        binding.edtPassword.setOnEditorActionListener { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                if (isValid)
//                {
//                //    callLoginApi()
//                }
//                true
//            } else {
//                false
//            }
//        }


    }
    private fun callLoginApi() {

        var map = HashMap<String,String>()
        map["role_id"] = "2"
        map["email"] = getString(binding.edtEmail)
        map["password"] = getString(binding.edtPassword)
        map["device_type"] = "android"
        map["device_id"] = SharedPref.getInstance(context).readString(SharedPref.FCM_TOKEN)

        liveData = viewModel?.getLoginLiveData(networkCall,
            map )
        liveData?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val userBaseResponse = it as BaseResponse<User>
            try {



                if(!TextUtils.isEmpty(it.data!!.token)){

                    AppManager.setUser(it.data)
                    var intent = Intent(activity, P2PActivity::class.java)
                    startActivity(intent)
                    activity?.finish()

                }


            } catch (e: Exception) {
            }
        })
    }
    private fun SignupWork() {
        binding.tvSignUp.setOnClickListener({ view ->
            replace(SignupFragment(),true,true)
        })
    }
    private fun callForgotApi() {

        if(getString(binding.fPass.edtEmail).isEmpty()){
            showErrorBar(context,AppConstants.ERROR_ENTER_VALID_EMAIL,binding.fPass.edtEmail)
            return
        }
        if (!isEmailValid(getString(binding.fPass.edtEmail))) {
            showErrorBar(context, AppConstants.ERROR_INVALID_EMAIL,binding.fPass.edtEmail)
            binding.fPass.edtEmail.requestFocus()
            return
        }

        var map = HashMap<String,String>()
        map["role_id"] = "2"
        map["email"] = getString(binding.fPass.edtEmail)

        verifyEmail = getString(binding.fPass.edtEmail)

        var  liveData = viewModel?.getForgotPasswordLiveData(networkCall,map)
        liveData?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val baseResponse = it as BaseResponse<Void>
            try {


//                showDialog(baseResponse.message, DialogInterface.OnClickListener { dialog, which ->
//
//                })
                forgotPass.state = BottomSheetBehavior.STATE_COLLAPSED;
                showSimpleDialog(baseResponse.message,true)

            } catch (e: Exception) {
            }
        })
    }
    private  fun showSimpleDialog(message: String?,isShow : Boolean){
        val builder = AlertDialog.Builder(activity!!)
        //set title for alert dialog
        builder.setTitle(R.string.app_name)
        //set message for alert dialog
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Ok"){dialogInterface, which ->

            if (forgotOtp.state != BottomSheetBehavior.STATE_EXPANDED && isShow) {
                forgotOtp.state = BottomSheetBehavior.STATE_EXPANDED
            }

            dialogInterface.dismiss()
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
        binding.edtEmail.isEnabled = false
        binding.edtPassword.isEnabled = false
        binding.btnLogin.isEnabled = false
        binding.tvForgotPass.isEnabled = false
        binding.tvSignUp.isEnabled = false
    }

    override fun handleError(message: String?, tag: Int, code: Int?) {
        hideKeyboard()
        showErrorBar(context, message)
        //toast(message)
    }

    private fun hideShowResetPassword() {
        val start: Int
        val end: Int
        if (shown!!) {
            shown = false
            start = binding.fReset.edtPassword.selectionStart
            end = binding.fReset.edtPassword.selectionEnd
            binding.fReset.edtPassword.transformationMethod = PasswordTransformationMethod()
            binding.fReset.edtPassword.setSelection(start, end)
            binding.fReset.passwordView.setImageResource(R.drawable.ic_hide_password)


        } else {
            shown = true
            start = binding.fReset.edtPassword.selectionStart
            end = binding.fReset.edtPassword.selectionEnd
            binding.fReset.edtPassword.transformationMethod = null
            binding.fReset.edtPassword.setSelection(start, end)
            binding.fReset.passwordView.setImageResource(R.drawable.ic_show_password)
        }
    }
    private fun hideShowConfrmPassword() {
        val start: Int
        val end: Int
        if (shown!!) {
            shown = false
            start = binding.fReset.edtConfPassword.selectionStart
            end = binding.fReset.edtConfPassword.selectionEnd
            binding.fReset.edtConfPassword.transformationMethod = PasswordTransformationMethod()
            binding.fReset.edtConfPassword.setSelection(start, end)
            binding.fReset.confPasswordView.setImageResource(R.drawable.ic_hide_password)


        } else {
            shown = true
            start = binding.fReset.edtConfPassword.selectionStart
            end = binding.fReset.edtConfPassword.selectionEnd
            binding.fReset.edtConfPassword.transformationMethod = null
            binding.fReset.edtConfPassword.setSelection(start, end)
            binding.fReset.confPasswordView.setImageResource(R.drawable.ic_show_password)
        }
    }

    private fun callVerifyOtpApi(email:String) {

        if(getString(binding.fotp.edtpin).isEmpty()){
            showErrorBar(context,AppConstants.ERROR_ENTER_OTP,binding.fotp.edtpin)
            return
        }
        if (binding.fotp.edtpin.length()<4) {
            showErrorBar(context, AppConstants.ERROR_ENTER_CORRECT_OTP,binding.fotp.edtpin)
            return
        }

        var map = HashMap<String,String>()
        map["email"] = email
        map["verification_code"] = getString(binding.fotp.edtpin)

        var  liveData = viewModel?.getForgotOtpVerifyPasswordLiveData(networkCall,map)
        liveData?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val baseResponse = it as BaseResponse<Void>
            try {

                forgotOtp.state = BottomSheetBehavior.STATE_COLLAPSED;

                if(forgotReset.state != BottomSheetBehavior.STATE_EXPANDED){
                    forgotReset.state = BottomSheetBehavior.STATE_EXPANDED
                }

            } catch (e: Exception) {
            }
        })
    }
    private fun callResetPasswordApi(email:String) {

        if(getString(binding.fReset.edtPassword).isEmpty()){
            showErrorBar(context,AppConstants.ERROR_ENTER_PASSWORD,binding.fReset.edtPassword)
            return
        }
        if(getString(binding.fReset.edtPassword).length < 5){
            showErrorBar(context,AppConstants.ERROR_PASSWORD_LENGTH,binding.fReset.edtConfPassword)
            return
        }

        if(getString(binding.fReset.edtConfPassword).isEmpty()){
            showErrorBar(context,AppConstants.ERROR_ENTER_CONFIRM_PASSWORD,binding.fReset.edtConfPassword)
            return
        }



        if(!getString(binding.fReset.edtConfPassword).equals(getString(binding.fReset.edtPassword))){
            showErrorBar(context,AppConstants.ERROR_PASSWORD_AND_CONFIRM_PASSWORD_SHOULD_BE_SAME,binding.fReset.edtConfPassword)
            return
        }

        var map = HashMap<String,String>()
        map["email"] = email
        map["password"] = getString(binding.fReset.edtPassword)

        var  liveData = viewModel?.getForgotResetPasswordLiveData(networkCall,map)
        liveData?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val baseResponse = it as BaseResponse<Void>
            try {

                forgotReset.state = BottomSheetBehavior.STATE_COLLAPSED;

               showSimpleDialog(baseResponse.message,false)

            } catch (e: Exception) {
            }
        })
    }
    companion object {
        /**
         * The constant RC_SIGN_IN.
         */
        private val RC_SIGN_IN = 43564
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }




}