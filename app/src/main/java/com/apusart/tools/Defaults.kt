package com.apusart.tools

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.apusart.evently_android.MainActivity
import com.apusart.evently_android.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object Defaults {
    const val baseUrl = "" //base url
}

object Tools {
    fun hideKeyboard(activity: Activity?) {
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}

object LoginTools {
    lateinit var context: Context
    val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    fun provideContext(c: Context) {
        context = c
    }
}