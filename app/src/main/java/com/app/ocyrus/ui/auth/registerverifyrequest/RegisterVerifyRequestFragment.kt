package com.app.ocyrus.auth.login


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProvider
import com.app.ocyrus.R
import com.app.ocyrus.app.auth.login.RegisterVerifyRequestViewModel
import com.app.ocyrus.base.BaseFragment
import com.app.ocyrus.base.BaseNavigator
import com.app.ocyrus.databinding.FragmentRegiseterRequestVerifyBinding

/**
 * The type RegisterVerifyRequestFragment fragment.
 */
class RegisterVerifyRequestFragment :
    BaseFragment<FragmentRegiseterRequestVerifyBinding, RegisterVerifyRequestViewModel>,
    BaseNavigator {


    internal lateinit var binding: FragmentRegiseterRequestVerifyBinding

    internal var viewModel: RegisterVerifyRequestViewModel? = null


    override val layoutId: Int
        get() {
            return R.layout.fragment_regiseter_request_verify
        }


    @SuppressLint("ValidFragment")
    constructor(fromLogout: Boolean) {
        // Required empty public constructor
    }

    constructor() {
        // Required empty public constructor
    }

    override fun getViewModel(): RegisterVerifyRequestViewModel {
        viewModel = ViewModelProvider(this).get(RegisterVerifyRequestViewModel::class.java)
        return viewModel as RegisterVerifyRequestViewModel
    }


    override fun onViewCreated(@NonNull view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //FacebookSdk.sdkInitialize(context)
        //FirebaseApp.initializeApp(context!!)
        binding = viewDataBinding as FragmentRegiseterRequestVerifyBinding
        viewModel?.setNavigator(this)
        allWork()

    }

    private fun allWork() {
        binding.imgBack.setOnClickListener {
            replace(LoginFragment(true), true, false)
        }


        binding.btnGotoEmial.setOnClickListener {
            // goBack()
            replace(LoginFragment(true), true, false)
        }


    }

    override fun handlingBackPressed(): Boolean {

        replace(LoginFragment(true), true, false)
        return true
    }


    /**
     * Block touchable.
     */
    private fun blockTouchable() {

    }

    override fun handleError(message: String?, tag: Int, code: Int?) {
        hideKeyboard()
        showErrorBar(context, message)
        //toast(message)
    }

    companion object {
        /**
         * The constant RC_SIGN_IN.
         */
        private val RC_SIGN_IN = 43566584
    }


}