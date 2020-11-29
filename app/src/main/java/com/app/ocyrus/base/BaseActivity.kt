/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.app.ocyruss.base

import android.annotation.TargetApi
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.app.ocyrus.R
import com.app.ocyrus.network.NetworkCall
import com.app.ocyrus.network.NetworkUtils
import com.app.ocyrus.utills.TouchImageView
import com.app.ocyrus.utills.Util
import com.app.ocyrus.utills.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 *
 * @param <T> the type parameter
 * @param <V> the type parameter
</V></T> */
abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseFragment.Callback {
    lateinit var networkCall: NetworkCall
    /**
     * The M progress dialog.
     */

    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private val mProgressDialog: ProgressDialog? = null
    /**
     * The M view data binding.
     */
    /**
     * Gets view data binding.
     *
     * @return the view data binding
     */
    var viewDataBinding: T? = null
        private set


    var container = R.id.container
    var containerFull = R.id.container_full



    @get:LayoutRes
    abstract val layoutId: Int


    private var mViewModel: V? = null

    abstract fun getViewModel(): V


    val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkConnected(applicationContext)


    fun setContainerId(id: Int) {
        container = id
    }


    override fun onFragmentAttached() {

    }


    override fun onFragmentDetached(tag: String) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        performDataBinding()
        listenFragmentBackstack()


    }


    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }


    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.cancel()
        }
    }


    fun openActivityOnTokenExpire() {
        //        startActivity(HomeActivity.newIntent(this));
        //        finish();
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    /**
     * Perform data binding.
     */
    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        //      mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        if (mViewModel != null) {
            viewDataBinding!!.lifecycleOwner = this
            viewDataBinding!!.executePendingBindings()
        }
    }

    /**
     * Listen fragment backstack.
     */
    private fun listenFragmentBackstack() {

        supportFragmentManager.addOnBackStackChangedListener {

            val manager = supportFragmentManager
            if (manager == null) return@addOnBackStackChangedListener
             try {
                    val currFrag = manager.findFragmentById(container) as BaseFragment<*, *>?
                    currFrag?.onResumeCallback()
                }catch (e: Exception) {
                    e.printStackTrace()
                }
        }
    }

    /**
     * Replace fragment.
     *
     * @param fragment       the fragment
     * @param animate        the animate
     * @param addToBackStack the add to back stack
     * @param clearBackStack the clear back stack
     */
    fun replaceFragment(fragment: androidx.fragment.app.Fragment, animate: Boolean,
                        addToBackStack: Boolean, clearBackStack: Boolean) {

        if (clearBackStack)
            clearFragmentBackStack()


        val supportFragmentManager = supportFragmentManager

        val transaction = supportFragmentManager.beginTransaction()


        if (animate)
           transaction.setCustomAnimations(
                    R.animator.slide_in_from_right,
                    R.animator.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.exit_to_right
            )

            /*transaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
            )*/

        transaction.replace(container, fragment, fragment.javaClass.simpleName)

        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)

        transaction.commit()
        //        transaction.commitAllowingStateLoss();
        //        supportFragmentManager.executePendingTransactions();
    }

    /**
     * Replace fragment.
     *
     * @param fragment       the fragment
     * @param animate        the animate
     * @param addToBackStack the add to back stack
     */
    fun replaceFragment(fragment: androidx.fragment.app.Fragment, animate: Boolean, addToBackStack: Boolean) {

        val supportFragmentManager = supportFragmentManager

        val transaction = supportFragmentManager.beginTransaction()


        if (animate)
            /*transaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
            )*/
            transaction.setCustomAnimations(
                    R.animator.slide_in_from_right,
                    R.animator.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.exit_to_right
            )

        transaction.replace(container, fragment, fragment.javaClass.simpleName)

        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)

        transaction.commit()
        //        transaction.commitAllowingStateLoss();
        //        supportFragmentManager.executePendingTransactions();
    }

    /**
     * Replace fragment.
     *
     * @param fragment          the fragment
     * @param animate           the animate
     * @param addToBackStack    the add to back stack
     * @param clearBackStack    the clear back stack
     * @param removeFragmentTag the remove fragment tag
     */
    fun replaceFragment(fragment: androidx.fragment.app.Fragment, animate: Boolean, addToBackStack: Boolean, clearBackStack: Boolean, removeFragmentTag: String?) {

        if (clearBackStack)
            clearFragmentBackStack()


        val supportFragmentManager = supportFragmentManager

        val transaction = supportFragmentManager.beginTransaction()

        if (removeFragmentTag != null) {
            val fragmentByTag = supportFragmentManager.findFragmentByTag(removeFragmentTag)

            if (fragmentByTag != null)
                getSupportFragmentManager().beginTransaction().remove(fragmentByTag).commit()
        }


        if (animate)
            /*transaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
            )*/
            transaction.setCustomAnimations(
                    R.animator.slide_in_from_right,
                    R.animator.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.exit_to_right
            )

        transaction.replace(container, fragment, fragment.javaClass.simpleName)

        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)

        transaction.commit()
        //        transaction.commitAllowingStateLoss();
        //        supportFragmentManager.executePendingTransactions();
    }


    /**
     * Add fragment.
     *
     * @param fragment       the fragment
     * @param animate        the animate
     * @param addToBackStack the add to back stack
     * @param clearBackStack the clear back stack
     */
    fun addFragment(fragment: androidx.fragment.app.Fragment, animate: Boolean, addToBackStack: Boolean, clearBackStack: Boolean) {

        if (clearBackStack)
            clearFragmentBackStack()

        val supportFragmentManager = supportFragmentManager

        val transaction = supportFragmentManager.beginTransaction()

        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)

        if (animate)
            /*transaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
            )*/
            transaction.setCustomAnimations(
                    R.animator.slide_in_from_right,
                    R.animator.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.exit_to_right
            )

        transaction.add(container, fragment, fragment.javaClass.simpleName)
        transaction.commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }
  fun addFragmentOnTop(fragment: androidx.fragment.app.Fragment, animate: Boolean, addToBackStack: Boolean, clearBackStack: Boolean) {

        if (clearBackStack)
            clearFragmentBackStack()

        val supportFragmentManager = supportFragmentManager

        val transaction = supportFragmentManager.beginTransaction()

        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)

        if (animate)
            /*transaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
            )*/
            transaction.setCustomAnimations(
                    R.animator.slide_in_from_right,
                    R.animator.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.exit_to_right
            )

        transaction.add(containerFull, fragment, fragment.javaClass.simpleName)
        transaction.commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }


    fun clearFragmentBackStack() {
        val fm = supportFragmentManager

//        while (supportFragmentManager.backStackEntryCount > 0) {
//            supportFragmentManager.popBackStackImmediate()
//        }

        if(fm.getBackStackEntryCount()>0){
            var name = getSupportFragmentManager().getBackStackEntryAt(0).getName();
            getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    override fun onBackPressed() {
        var consumedByFragment = false
        try {
            val frag = supportFragmentManager.findFragmentById(container) as BaseFragment<*, *>?
            if (frag != null) {
                consumedByFragment = frag.handlingBackPressed()
            }

            if (!consumedByFragment) {
                if (supportFragmentManager.backStackEntryCount > 0)
                    supportFragmentManager.popBackStack()
                else

                    super.onBackPressed()
            }
        }catch (e:java.lang.Exception){
            super.onBackPressed()
        }
    }

    fun popFragment() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
        else
            super.onBackPressed()
    }

    fun popBackStackFragment(id: String): Boolean {
        var isPop = false
        try {
            if (supportFragmentManager.backStackEntryCount > 0) {
                isPop = true
                supportFragmentManager.popBackStack(id, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return isPop
    }

    fun toast(msg: String) {
        Toast.makeText(this, Util.nullCheck(msg), Toast.LENGTH_SHORT).show()
    }

    fun pushDown(view: View, clickListener: View.OnClickListener) {

    }

    fun showProgress(context: Context) {
        Util.showProgress(context)
    }

    fun hideProgress() {
        Util.hideProgress()
    }

    fun goBack() {
        try {
            onBackPressed()
        } catch (e: Exception) {
        }

    }

    fun showKeyboard(context: Context) {
        Util.showKeyboard(context)
    }

    fun getString(editText: EditText): String {
        return Utils.getString(editText)
    }

    fun getString(textView: TextView): String {
        return Utils.getString(textView)
    }

    fun isEmpty(charSequence: CharSequence): Boolean {
        return TextUtils.isEmpty(charSequence)
    }

    fun showSuccessBar(context: Context, msg: String) {
        Utils.showSuccessSneaker(context, msg)
    }

    fun showErrorBar(context: Context, msg: String) {
        Utils.showErrorSneaker(context, msg)
    }

    fun showErrorBar(context: Context, msg: String, view: View) {
        Utils.showErrorSneaker(context, msg, view)
    }

    fun showWarning(context: Context, msg: String) {
        Utils.showWarningSneaker(context, msg)
    }

    fun showWarning(context: Context, msg: String, view: View) {
        Utils.showWarningSneaker(context, msg, view)
    }

    fun showImageDialog(url: String?) {
        if (url == null)
            return

        val dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.show()
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.image_dialog)
        val touchImageView = dialog.findViewById<TouchImageView>(R.id.img)
        val imgBack = dialog.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener { v -> dialog.dismiss() }

//        Glide.with(this)
//                .setDefaultRequestOptions(RequestOptions()
//                        .placeholder(R.drawable.placeholder)
//                        .error(R.drawable.placeholder)
//                )
//                .load(Uri.parse(url)).into(touchImageView)
    }

    fun showImageDialogWithUrl(url: String?) {
        if (url == null)
            return

        val dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.show()
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.image_dialog)
        val touchImageView = dialog.findViewById<TouchImageView>(R.id.img)
        val imgBack = dialog.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener { v -> dialog.dismiss() }

//        Glide.with(this)
//                .setDefaultRequestOptions(RequestOptions()
//                        .placeholder(R.drawable.placeholder)
//                        .error(R.drawable.placeholder)
//                )
//                .load(url).into(touchImageView)
    }

    companion object {
        fun <T> nullCheck(str: T?): T? {
            return str ?: "" as T
        }
    }


}