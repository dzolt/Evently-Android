package com.apusart.evently_android.logged.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.apusart.evently_android.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main_logged.*

class TestViewModel: ViewModel() {
    val user = MutableLiveData(Firebase.auth.currentUser)
}

class MainLoggedActivity: AppCompatActivity(R.layout.main_logged) {
    val viewModel: TestViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.user.observe(this, Observer {
            text_test1.text = "${it?.email ?: "brak emailu"} ${it?.isAnonymous ?: "normalny anonimus"}"
        })
    }
}