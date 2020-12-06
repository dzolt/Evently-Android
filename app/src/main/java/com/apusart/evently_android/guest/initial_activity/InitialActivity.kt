package com.apusart.evently_android.guest.initial_activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.apusart.api.local_data_source.db.EventlyDatabase
import com.apusart.appComponent
import com.apusart.evently_android.guest.login_activity.LoginActivity
import com.apusart.evently_android.logged.main.MainLoggedActivity
import com.apusart.tools.LoginTools
import com.facebook.FacebookSdk
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import javax.inject.Inject

class InitialActivity: AppCompatActivity() {
    @Inject
    lateinit var viewModel: InitialActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        EventlyDatabase.initialize(applicationContext)
        LoginTools.context = applicationContext

        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel.currentUser.observe(this, Observer {
            if(it == null) {
                startActivity(Intent(applicationContext, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))

            } else {
                startActivity(Intent(applicationContext, MainLoggedActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))

            }
        })
    }

    override fun onStart() {
        super.onStart()
        Firebase.initialize(applicationContext)

    }
}