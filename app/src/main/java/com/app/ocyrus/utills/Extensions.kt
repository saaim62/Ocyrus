package com.spot2win.app.ui.base

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.annotation.RawRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.ocyrus.R
import com.app.ocyrus.utills.AppManager
import com.bumptech.glide.Glide

@set:BindingAdapter("imageUrl")
var ImageView.imageUrl: String?
    get() = imageUrl
    set(value) {
        if (URLUtil.isValidUrl(value))
            Glide.with(context).load(value).placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(this)
        else
            Glide.with(context).load(R.drawable.placeholder).into(this)
    }

@set:BindingAdapter("imageUrlOptions")
var ImageView.imageUrlOptions: ImageOptions
    get() = imageUrlOptions
    set(value) {
        if (URLUtil.isValidUrl(value.imageUrl))
            Glide.with(context).load(value.imageUrl).placeholder(value.placeholder).error(value.error)
                    .apply {
                        if (value.isCenterCrop) this.centerCrop()
                        else this.fitCenter() }
                    .into(this)
        else
            Glide.with(context).load(value.placeholder).apply { if (value.isCenterCrop) this.centerCrop() else this.fitCenter() }.into(this)
    }

@set:BindingAdapter("imageLocal")
var ImageView.imageLocal: Int?
    @RawRes @DrawableRes @Nullable get() = imageLocal
    set(@RawRes @DrawableRes @Nullable value) {
        Glide.with(context).load(value).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).centerCrop().into(this)
    }

@set:BindingAdapter("visibleOrGone")
var View.visibleOrGone
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

@set:BindingAdapter("visible")
var View.visible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

/*var Activity.toast: String?
    get() = toast
    set(value) {
        if (!TextUtils.isEmpty(value))
            Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }*/


fun showToast(msg: String) {
    if (!TextUtils.isEmpty(msg))
        Toast.makeText(AppManager.getAppContext(), msg, Toast.LENGTH_SHORT).show()
}

var RecyclerView.ViewHolder.dialog: String
    get() = dialog
    set(value) {
        AlertDialog.Builder(itemView.context).apply {
            setTitle(itemView.context.getString(R.string.app_name))
            setMessage(value)
            setPositiveButton(
                    itemView.context.getString(R.string.ok)
            ) { dialog, which ->
                dialog.dismiss()
            }
        }.create().show()
    }

var Context.dialog: String
    get() = dialog
    set(value) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.app_name))
            setMessage(value)
            setPositiveButton(
                    getString(R.string.ok)
            ) { dialog, which ->
                dialog.dismiss()
            }
        }.create().show()
    }

data class ImageOptions(val imageUrl: String? = "", @DrawableRes val placeholder: Int = R.drawable.placeholder, @DrawableRes val error: Int = R.drawable.placeholder, val isCenterCrop: Boolean = false)


fun Activity.showListDialog(title: String, arrayList: ArrayList<String>, positiveClick: DialogInterface.OnClickListener, negativeClick: DialogInterface.OnClickListener) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setPositiveButton(getString(R.string.ok), positiveClick)
        setNegativeButton(getString(R.string.btn_cancel), negativeClick)
    }.create().show()
}

fun pushDown(view: View, clickListener: View.OnClickListener) {

}

