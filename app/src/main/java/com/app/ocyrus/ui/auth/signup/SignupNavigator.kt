package com.app.ocyruss.auth.signup

/**
 * The interface Signup navigator.
 */
interface SignupNavigator {
    /**
     * Handle error.
     *
     * @param message the message
     * @param tag     the tag
     */
    fun handleError(message: String, tag: Int)
}
