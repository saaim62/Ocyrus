package com.app.ocyruss.base

/**
 * The interface Change password navigator.
 */
interface BaseNavigator {
    /**
     * Handle error.
     *
     * @param message the message
     * @param tag     the tag
     */
    fun handleError(message: String?, tag: Int, code: Int?)
}
