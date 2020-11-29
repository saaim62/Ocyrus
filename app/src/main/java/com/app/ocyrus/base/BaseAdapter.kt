package com.app.ocyrus.base

import android.content.Context
import android.view.LayoutInflater
import com.app.ocyrus.utills.recyclerview.MultiItemTypeAdapter
import com.app.ocyrus.utills.recyclerview.base.ItemViewDelegate
import com.app.ocyrus.utills.recyclerview.base.ViewHolder



abstract class BaseAdapter<T>(var context: Context, protected var mLayoutId: Int, protected var mDatas: List<T>) : MultiItemTypeAdapter<T>(context, mDatas) {
    protected var mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(context)

        addItemViewDelegate(object : ItemViewDelegate<T> {
            override fun getItemViewLayoutId(): Int {
                return mLayoutId
            }

            override fun isForViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun convert(holder: ViewHolder, t: T, position: Int) {
                this@BaseAdapter.convert(holder, t, position)
            }
        })
    }

    protected abstract fun convert(holder: ViewHolder, t: T, position: Int)


}
