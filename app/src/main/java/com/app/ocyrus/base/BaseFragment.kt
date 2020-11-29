

package com.app.ocyrus.base

import android.app.AlertDialog
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import androidx.fragment.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.app.ocyrus.R
import com.app.ocyrus.network.NetworkCall
import com.app.ocyrus.utills.FileUtils
import com.app.ocyrus.utills.SharedPref
import com.app.ocyrus.utills.Util
import com.app.ocyrus.utills.Utils


import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by amitshekhar on 09/07/17.
 *
 * @param <T> the type parameter
 * @param <V> the type parameter
</V></T> */
abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment() {

    internal var duration = 2500
    var mediaPlayer: MediaPlayer? = null


    /**
     * Gets base activity.
     *
     * @return the base activity
     */
    var baseActivity: BaseActivity<*, *>? = null
        private set
    private var mRootView: View? = null
    /**
     * Gets view data binding.
     *
     * @return the view data binding
     */
    var viewDataBinding: T? = null
        private set
    private var mViewModel: Any? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    //    public abstract int getBindingVariable();

    abstract val layoutId: Int

    abstract fun getViewModel(): Any?

    fun pushDown(view: View, clickListener: View.OnClickListener) {
       // PushDownAnim.setPushDownAnimTo(view).setOnClickListener(clickListener)
    }

    /**
     * Is network connected boolean.
     *
     * @return the boolean
     */
    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected

    /**
     * Gets network call.
     *
     * @return the network call
     */
    val networkCall: NetworkCall
        get() = (activity as BaseActivity<*,*>).networkCall

    /**
     * Gets container.
     *
     * @return the container
     */
    val container: Int
        get() = (activity as BaseActivity<*, *>).container

    /**
     * Gets country code.
     *
     * @return the country code
     */
    val countryCode: String
        get() = context!!.resources.configuration.locale.country

    internal var alertDialog: AlertDialog? = null


    /**
     * On resume callback.
     */
    open fun onResumeCallback() {}

    /**
     * Handling back pressed boolean.
     *
     * @return the boolean
     */
    open fun handlingBackPressed(): Boolean {
        return false
    }

    /**
     * On attach.
     *
     * @param context the context
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
            activity!!.onFragmentAttached()
        }
    }

    fun setUserTypeToUser() {
        SharedPref.getInstance(context).writeString(SharedPref.USER_TYPE, SharedPref.USER)
    }

    /**
     * Set user type to sp.
     */
    fun setUserTypeToSP() {
        SharedPref.getInstance(context).writeString(SharedPref.USER_TYPE, SharedPref.SERVICE_PROVIDER)
    }

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        setHasOptionsMenu(false)

    }

    /**
     * On create view view.
     *
     * @param inflater           the inflater
     * @param container          the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    /**
     * On detach.
     */
    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }


    /**
     * On view created.
     *
     * @param view               the view
     * @param savedInstanceState the saved instance state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel

        if (mViewModel != null) {
//            viewDataBinding!!.lifecycleOwner = lifecycle
            viewDataBinding!!.executePendingBindings()
        }
        hideKeyboard()
        try {

        } catch (e: java.lang.Exception) {
        }
    }

    /**
     * Open activity on token expire.
     */
    fun openActivityOnTokenExpire() {
        if (baseActivity != null) {
            baseActivity!!.openActivityOnTokenExpire()
        }
    }

    /**
     * The interface Callback.
     */
    interface Callback {

        /**
         * On fragment attached.
         */
        fun onFragmentAttached()

        /**
         * On fragment detached.
         *
         * @param tag the tag
         */
        fun onFragmentDetached(tag: String)
    }


    /**
     * Replace.
     *
     * @param fragment       the fragment
     * @param animate        the animate
     * @param addToBackStack the add to back stack
     * @param clearBackStack the clear back stack
     */
    fun replace(fragment: BaseFragment<*, *>, animate: Boolean, addToBackStack: Boolean, clearBackStack: Boolean) {
        try {
            (activity as BaseActivity<*, *>).replaceFragment(fragment, animate, addToBackStack, clearBackStack)
            checBottomTabVisibility(fragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * Replace.
     *
     * @param fragment       the fragment
     * @param animate        the animate
     * @param addToBackStack the add to back stack
     * @param clearBackStack the clear back stack
     * @param name           the name
     */
    fun replace(fragment: BaseFragment<*, *>, animate: Boolean, addToBackStack: Boolean, clearBackStack: Boolean, name: String) {
        try {
            (activity as BaseActivity<*, *>).replaceFragment(fragment, animate, addToBackStack, clearBackStack, name)
            checBottomTabVisibility(fragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * Replace.
     *
     * @param fragment       the fragment
     * @param animate        the animate
     * @param addToBackStack the add to back stack
     */
    fun replace(fragment: BaseFragment<*, *>, animate: Boolean, addToBackStack: Boolean) {
        try {
            (activity as BaseActivity<*, *>).replaceFragment(fragment, animate, addToBackStack)
            checBottomTabVisibility(fragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun replace(fragment: BaseFragment<*, *>, animate: Boolean) {
        try {
            (activity as BaseActivity<*, *>).replaceFragment(fragment, animate, true)
            checBottomTabVisibility(fragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun replace(fragment: BaseFragment<*, *>) {
        try {
            (activity as BaseActivity<*, *>).replaceFragment(fragment, true, true)
            checBottomTabVisibility(fragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun addFragment(fragment: BaseFragment<*, *>, animate: Boolean, addToBackStack: Boolean) {
        try {
            (activity as BaseActivity<*, *>).addFragment(fragment, animate, addToBackStack, false)
            checBottomTabVisibility(fragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

 fun addFragmentOnTop(fragment: BaseFragment<*, *>, animate: Boolean, addToBackStack: Boolean) {
        try {
            (activity as BaseActivity<*, *>).addFragmentOnTop(fragment, animate, addToBackStack, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
fun selcetPositionTab(){
    if (baseActivity != null) {
//        baseActivity!!.selcetPositionTab()
    }
}

    fun addFragmentWithTarget(fragment: BaseFragment<*, *>, animate: Boolean, addToBackStack: Boolean) {
        try {
            (activity as BaseActivity<*, *>).addFragment(fragment, animate, addToBackStack, false)
            checBottomTabVisibility(fragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun hideBottomTabVisibility(fragment: BaseFragment<*, *>) {

    }



    fun checBottomTabVisibility(fragment: BaseFragment<*, *>) {

//        if(fragment is HomeFragment || fragment is CategoryFragment || fragment is StoreListFragment || fragment is MoreFragment){}
//        if(fragment is HomeFragment || fragment is MoreFragment){
//            (context as MainActivity).visibleBottomTab(1)
//        }else{
//            (context as MainActivity).visibleBottomTab(0)
//        }

//        (context as MainActivity).visibleBottomTab(1)
    }

    fun popFragment() {
        (activity as BaseActivity<*, *>).popFragment()
    }

    /**
     * Go back.
     */
    fun goBack() {
        try {
            (activity as BaseActivity<*, *>).onBackPressed()
        } catch (e: Exception) {
        }

    }

    fun goBack(message: String?) {
        try {
            message?.let {
                showSuccessBar(context, nullCheck(message))
                Handler().postDelayed({ (activity as BaseActivity<*, *>).onBackPressed() }, duration.toLong())
            }
        } catch (e: Exception) {
            Log.e("Error",e.message+"")
        }

    }

    /**
     * Hide keyboard.
     */
    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    fun showDialog(message: String, positiveClick: View.OnClickListener?): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(activity)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_fill_service_layout, null)
        dialogBuilder.setView(dialogView)

        if (alertDialog == null) {
            alertDialog = dialogBuilder.create()
            alertDialog!!.show()
            alertDialog!!.setCancelable(false)
        } else if (!alertDialog!!.isShowing) {
            alertDialog = dialogBuilder.create()
            alertDialog!!.show()
            alertDialog!!.setCancelable(false)
        }

        val btnPositive = dialogView.findViewById<androidx.cardview.widget.CardView>(R.id.cv_logout)
        val btnNegative = dialogView.findViewById<androidx.cardview.widget.CardView>(R.id.cv_cancel)
        val tvMessage = dialogView.findViewById<TextView>(R.id.tv_message)

        val tv_positive = dialogView.findViewById<TextView>(R.id.tv_positive)

        tv_positive.text = "Yes"

        tvMessage.text = message ?: "Are you sure you want to perform this action?"
        pushDown(btnNegative, View.OnClickListener { alertDialog?.dismiss() })

        if (positiveClick != null)
            pushDown(btnPositive, positiveClick)
        else
            pushDown(btnPositive, View.OnClickListener { alertDialog!!.dismiss() })

        return alertDialog as AlertDialog
    }

    fun showDialog(message: String, title: String, positiveButtonName: String, positiveClick: View.OnClickListener?, negativeClick: View.OnClickListener?, hideNegativeButton: Boolean): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(activity)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_fill_service_layout, null)
        dialogBuilder.setView(dialogView)

        if (alertDialog == null) {
            alertDialog = dialogBuilder.create()
            alertDialog!!.show()
            alertDialog!!.setCancelable(false)
        } else if (!alertDialog!!.isShowing) {
            alertDialog = dialogBuilder.create()
            alertDialog!!.show()
            alertDialog!!.setCancelable(false)
        }

        val btnPositive = dialogView.findViewById<androidx.cardview.widget.CardView>(R.id.cv_logout)
        val btnNegative = dialogView.findViewById<androidx.cardview.widget.CardView>(R.id.cv_cancel)
        val tvMessage = dialogView.findViewById<TextView>(R.id.tv_message)
        val tv_positive = dialogView.findViewById<TextView>(R.id.tv_positive)
        val tv_negative = dialogView.findViewById<TextView>(R.id.tv_negative)
        val tv_title = dialogView.findViewById<TextView>(R.id.tv_title)

        btnNegative.visibility = if (hideNegativeButton) View.GONE else View.VISIBLE
        tv_positive.text = if(!isEmpty(positiveButtonName)) positiveButtonName else "Proceed"
        tv_negative.text = if(!isEmpty(positiveButtonName)) { if(positiveButtonName.equals("Cancel")) "Close" else "Cancel" } else "Cancel"

        tv_title.text = title ?: "Alert"
        tvMessage.text = if(!isEmpty(message)) message else "Are you sure you want to perform this action?"

        if (positiveClick != null)
            pushDown(btnPositive, positiveClick)
        else
            pushDown(btnPositive, View.OnClickListener { alertDialog!!.dismiss() })

        if (negativeClick != null)
            pushDown(btnNegative, negativeClick)
        else
            pushDown(btnNegative, View.OnClickListener { alertDialog!!.dismiss() })

        return alertDialog as AlertDialog
    }

    /**
     * Toast.
     *
     * @param msg the msg
     */
    fun toast(msg: String?) {
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(context, Util.nullCheck(msg), Toast.LENGTH_SHORT).show()
    }

    /**
     * Show progress.
     *
     * @param context the context
     */
    fun showProgress(context: Context?) {
        context?.let {
            Util.showProgress(context)
        }
    }

    /**
     * Hide progress.
     */
    fun hideProgress() {
        Util.hideProgress()
    }

    /**
     * Show keyboard.
     *
     * @param context the context
     */
    fun showKeyboard(context: Context?) {
        context?.let {
            Util.showKeyboard(context)
        }
    }

    /**
     * Gets string.
     *
     * @param editText the edit text
     * @return the string
     */
    fun getString(editText: EditText): String {
        return Utils.getString(editText)
    }

    /**
     * Gets string.
     *
     * @param textView the text view
     * @return the string
     */
    fun getString(textView: TextView?): String {
        if (textView == null)
            return "";
        else
            return Utils.getString(textView)
    }

    /**
     * Is empty boolean.
     *
     * @param charSequence the char sequence
     * @return the boolean
     */
    fun isEmpty(charSequence: CharSequence?): Boolean {
        return TextUtils.isEmpty(charSequence)
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun makeEditable(editable: Boolean, view: View) {
        //        view.setFocusable(editable);
        //        view.setFocusableInTouchMode(editable);
        view.isEnabled = editable
        //        view.setClickable(editable);

        if (view is EditText)
            view.isCursorVisible = editable

        view.isClickable = editable
    }
    private var lastClickedTime: Long = 0
    fun getLocation(context: Context, fragment: Fragment?) {
//        val currentLong = System.currentTimeMillis()
//        var intent: Intent? = null
//        if (currentLong - lastClickedTime > 5000) {
//            try {
//                lastClickedTime = currentLong
//                val fields = Arrays.asList<Place.Field>(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
//                intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                    .build(context)
//
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//            if (fragment != null)
//                fragment.startActivityForResult(intent, SharedPref.REQUEST_SEARCH_CODE)
//            else
//                startActivityForResult(intent, SharedPref.REQUEST_SEARCH_CODE)
//        }
    }
    /**
     * Show success bar.
     *
     * @param context the context
     * @param msg     the msg
     */
    fun showSuccessBar(context: Context?, msg: String?) {
        context?.let {
            Utils.showSuccessSneaker(context, msg)
        }
    }

    /**
     * Show error bar.
     *
     * @param context the context
     * @param msg     the msg
     */
    fun showErrorBar(context: Context?, msg: String?) {
        context?.let {
            Utils.showErrorSneaker(context, msg ?: "Something went wrong")
        }
    }

    /**
     * Show error bar.
     *
     * @param context the context
     * @param msg     the msg
     * @param view    the view
     */
    fun showErrorBar(context: Context?, msg: String, view: View) {
        context?.let {
            Utils.showErrorSneaker(context, msg, view)
        }
    }

    fun showErrorTimeExtendBar(context: Context?, msg: String, view: View) {
        context?.let {
            Utils.showErrorExtendSneaker(context, msg, view)
        }
    }

    /**
     * Show warning.
     *
     * @param context the context
     * @param msg     the msg
     */
    fun showWarning(context: Context?, msg: String) {
        context?.let {
            Utils.showWarningSneaker(context, msg)
        }
    }

    /**
     * Show warning.
     *
     * @param context the context
     * @param msg     the msg
     * @param view    the view
     */
    fun showWarning(context: Context?, msg: String, view: View) {
        context?.let {
            Utils.showWarningSneaker(context, msg, view)
        }
    }

    /**
     * Clear back stack.
     */
    fun clearBackStack() {
        (activity as BaseActivity<*, *>).clearFragmentBackStack()
    }

    fun getBitmapFromURL(src: String): Bitmap? {
        try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    fun play(soundName: String) {
        try {
            if (mediaPlayer == null) {
                val resID = resources.getIdentifier(soundName, "raw", context!!.packageName)
                mediaPlayer = MediaPlayer.create(context, resID)
                mediaPlayer!!.isLooping = false
            }

            if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
                mediaPlayer!!.start()
            }
        } catch (e: Exception) {
        }

    }


    fun stopPlayer() {
        try {
            if (mediaPlayer != null && mediaPlayer!!.isPlaying)
                mediaPlayer!!.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onStop() {
        super.onStop()
    }

    fun showSocialBottomSheet(/*petsUserDashboard: PetsUserDashboard*/) {
        /*val itemCount: Int

        if (!AppManager.isUserLoggedIn())
            itemCount = 1
        else
            itemCount = 3

        val items = arrayOfNulls<CharSequence>(itemCount)
        items[0] = "Invite Friend"
        items[1] = "Write Feedback"

        if (AppManager.isUserLoggedIn())
            items[2] = "Logout"

        val icons = arrayOfNulls<Drawable>(itemCount)
        icons[0] = activity!!.resources.getDrawable(R.drawable.ic_invite_friend)
        icons[1] = activity!!.resources.getDrawable(R.drawable.ic_feedback)

        if (AppManager.isUserLoggedIn())
            icons[2] = activity!!.resources.getDrawable(R.drawable.logout_black)


        val builder = BottomSheet.Builder(context!!)
        builder.setTitle("Choose Action")
                .setItems(items, icons) { dialogInterface, i ->
                    when (i) {
                        0 -> petsUserDashboard.inviteFriendDialog() //replace(ChangePasswordFragment())//sendToWebview("Privacy Policy", AppConstants.URL_PRIVACY_POLICY)

                        1 -> petsUserDashboard.showFeedbackDialog() //replace(ChangePasswordFragment())//sendToWebview("Privacy Policy", AppConstants.URL_PRIVACY_POLICY)

                        2 -> showDialog("Are you sure you want to logout?", View.OnClickListener { AppManager.logoutUser(context!!) })
                    }
                }
                .show()*/
    }

    fun guestGoToLogin(){
//        replace(LoginFragment(), true, true, false)
    }

    fun getFile(uri: Uri?): File? {
        return if (uri == null) null else File(FileUtils.getPath(context, uri))
    }


    companion object {

        /**
         * Null check t.
         *
         * @param <T> the type parameter
         * @param str the str
         * @return the t
        </T> */
        fun <T> nullCheck(str: T?): T? {
            return str ?: "" as T
        }
    }

    @Throws(IOException::class)
    fun getDestinationFilePath(type: Int): File {

        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "compressed" + timeStamp + "_"
        /*File storageDir = Environment.getExternalStoragePublicDirectory(
                type == 1 ? Environment.DIRECTORY_PICTURES : Environment.DIRECTORY_MOVIES);*/
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
                fileName, /* prefix */
                if (type == 1) ".jpg" else ".mp4", /* suffix */
                storageDir      /* directory */
        )

        // Get the path of the file created
        //destinationFilePath = file.getAbsolutePath();
        return storageDir
    }

    fun getMultipartEntity(file: File, key: String,type:String): MultipartBody.Part {

        //val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val requestFile = RequestBody.create(type.toMediaTypeOrNull(), file)

        // MultipartBody.Part is used to send also the actual file name

        return MultipartBody.Part.createFormData(key, file.name, requestFile)
    }

}