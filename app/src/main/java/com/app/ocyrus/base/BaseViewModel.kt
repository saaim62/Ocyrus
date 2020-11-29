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

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import java.lang.ref.WeakReference


/**
 * Created by amitshekhar on 07/07/17.
 *
 * @param <N> the type parameter
</N> */
abstract class BaseViewModel<N> (application: Application) : AndroidViewModel(application) {

    /**
     * The M navigator.
     */
    private var mNavigator: WeakReference<N>? = null


    /**
     * Gets navigator.
     *
     * @return the navigator
     */
    fun getNavigator(): N? {
        return mNavigator?.get()
    }

    /**
     * Sets navigator.
     *
     * @param navigator the navigator
     */
    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }


}
