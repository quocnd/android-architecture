package com.quoc.coroutine.extension

import androidx.fragment.app.Fragment

fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}