package com.app.ocyrus.auth.signup

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.ocyrus.AppConstants
import com.app.ocyrus.ImagePickerActivity
import com.app.ocyrus.ImagePickerActivity.PickerOptionListener
import com.app.ocyrus.R
import com.app.ocyrus.base.BaseFragment
import com.app.ocyrus.base.BaseNavigator
import com.app.ocyrus.databinding.FragmentSignupBinding
import com.app.ocyrus.utills.FileUtils
import com.app.ocyrus.utills.Util
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File


/**
 * The type Signup fragment.
 */
/**
 * Instantiates a new Signup fragment.
 */
class SignupFragment : BaseFragment<FragmentSignupBinding, SignupViewModel>(), BaseNavigator {

    private var mCropImageUri: Uri? = null
    internal var isCameFromImagePicker = false

    internal var viewModel: SignupViewModel? = null

    internal lateinit var binding: FragmentSignupBinding

    override val layoutId: Int get() = R.layout.fragment_signup


    private val isValid: Boolean
        get() {
//            if (mCropImageUri == null ) {
//                showErrorBar(getContext(), AppConstants.ERROR_Image)
//                return false
//            }

            if (isEmpty(getString(binding.edtFname))) {
                showErrorBar(context, AppConstants.ERROR_ENTER_VALID_FNAME, binding.edtFname)
                binding.edtFname.requestFocus()
                return false
            }
            if (isEmpty(getString(binding.edtLName))) {
                showErrorBar(context, AppConstants.ERROR_ENTER_VALID_LNAME, binding.edtLName)
                binding.edtLName.requestFocus()
                return false
            }

            if (!isEmailValid(getString(binding.edtEmail))) {
                showErrorBar(context, AppConstants.ERROR_INVALID_EMAIL, binding.edtEmail)
                binding.edtEmail.requestFocus()
                return false
            }

            if (isEmpty(getString(binding.edtPhone))) {
                showErrorBar(context, AppConstants.ERROR_ENTER_VALID_MOBILE, binding.edtPhone)
                binding.edtPhone.requestFocus()
                return false
            }
            if (getString(binding.edtPhone).length < 10) {
                showErrorBar(context, AppConstants.ERROR_ENTER_VALID_PHONE_NUMBER, binding.edtPhone)
                binding.edtPhone.requestFocus()
                return false
            }
            if (isEmpty(getString(binding.edtPassword))) {
                showErrorBar(context, AppConstants.ERROR_ENTER_PASSWORD, binding.edtPassword)
                binding.edtPassword.requestFocus()
                return false
            }

            if (getString(binding.edtPassword).length < 5) {
                showErrorBar(context, AppConstants.ERROR_PASSWORD_LENGTH, binding.edtPassword)
                binding.edtPassword.requestFocus()
                return false
            }


            return true
        }

    /**
     * Gets view model.
     *
     * @return the view model
     */
    override fun getViewModel(): SignupViewModel {
        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        return viewModel as SignupViewModel
    }

    /**
     * On view created.
     *
     * @param view               the view
     * @param savedInstanceState the saved instance state
     */
    override fun onViewCreated(@NonNull view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWork()
        allWork()

        // replace(new OTPFragment(), true, true)
    }

    /**
     * Init work.
     */
    private fun initWork() {
        binding = viewDataBinding as FragmentSignupBinding
        viewModel?.setNavigator(this)
        binding.imgBack.setOnClickListener({ view1 -> goBack() })
        binding.tvlogin.setOnClickListener({ view1 -> goBack() })

    }

    /**
     * All work.
     */
    private fun allWork() {

        binding.chnageImage.setOnClickListener({ view1 -> pickImage() })

        binding.btnRegister.setOnClickListener {
            if (isValid) {
                callRegisterApi()
            }
        }
        binding.passwordView.setOnClickListener {
            hideShowPassword()
        }


        // binding.tvPP.setOnClickListener { view -> goToPrivacyPolicy() }
        // binding.tvTmc.setOnClickListener { view -> goToTmc() }

    }


    /**
     * Call register api.
     */
    private fun callRegisterApi() {
        var file: File? = null
        if (mCropImageUri != null) {
            file = File(FileUtils.getPath(activity, mCropImageUri))
        }


        viewModel?.doRegister(
            networkCall,
            "2",
            getString(binding.edtFname),
            getString(binding.edtLName),
            getString(binding.edtEmail),
            getString(binding.edtPhone),
            getString(binding.edtPassword),
            "",
            "android",
            file


        )?.observe(viewLifecycleOwner, Observer {

//            replace(RegisterVerifyRequestFragment(),true,true)

//            showSuccessBar(activity,it.message)
//            Handler().postDelayed({  goBack() }, duration.toLong())
            Util.showAlertBox(
                activity,
                it.message,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    goBack()
                })

        })
    }

    private var shown: Boolean? = false
    private var shownConf: Boolean? = false

    private fun pickImage() {
        showImagePickerOptions()


    }


    private fun showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(activity, object : PickerOptionListener {
            override fun onChooseGallerySelected() {
//                callCameraIntent()
                callGallaryIntent()
            }

            override fun onTakeCameraSelected() {
                callCameraIntent()
            }
//            fun onTakeCameraSelected() {
//                callCameraIntent()
//            }
//
//            fun onChooseGallerySelected() {
////                launchGalleryIntent();
//            }
        })
    }

    private fun callCameraIntent() {
        val intent = Intent(activity, ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity.REQUEST_IMAGE_CAPTURE
        )

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000)
        startActivityForResult(
            intent,
            ImagePickerActivity.REQUEST_IMAGE_CAPTURE
        )
    }

    private fun callGallaryIntent() {
        val intent = Intent(activity, ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity.REQUEST_GALLERY_IMAGE
        )

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000)
        startActivityForResult(
            intent,
            ImagePickerActivity.REQUEST_GALLERY_IMAGE
        )
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

    override fun handleError(message: String?, tag: Int, code: Int?) {
        showErrorBar(context, Util.nullCheck(message))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ImagePickerActivity.REQUEST_IMAGE_CAPTURE || requestCode == ImagePickerActivity.REQUEST_GALLERY_IMAGE) {

            if (resultCode == AppCompatActivity.RESULT_OK) {
                val uri = data!!.getParcelableExtra<Uri>("path")
                try {
                    mCropImageUri = uri
                    setUserImage(uri)

                    // You can update this bitmap to your server
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//
//                // loading profile image from local cache
//                loadProfile(uri.toString());
                } catch (e: Exception) {
                }
            }


        }

    }

    private fun setUserImage(imageUri: Uri?) {
        if (imageUri != null) {
            Glide.with(activity!!)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                )
                .load(imageUri).into(binding.user)
        }
    }

    companion object {
        /**
         * The constant RC_SIGN_IN.
         */
        private val RC_SIGN_IN = 43564
    }
}