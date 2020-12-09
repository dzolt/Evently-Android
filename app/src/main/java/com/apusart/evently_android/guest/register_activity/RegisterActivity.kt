package com.apusart.evently_android.guest.register_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.apusart.api.Resource
import com.apusart.appComponent
import com.apusart.evently_android.MainActivity
import com.apusart.evently_android.R
import com.apusart.evently_android.databinding.RegisterBinding
import com.apusart.evently_android.guest.login_activity.LoginActivity
import com.apusart.evently_android.logged.main.MainLoggedActivity
import com.apusart.tools.AppViewModelFactory
import com.apusart.tools.Tools
import kotlinx.android.synthetic.main.information_modal.view.*
import kotlinx.android.synthetic.main.register.*
import javax.inject.Inject

class RegisterActivity: AppCompatActivity() {

    private val viewModel: RegisterActivityViewModel by viewModels { AppViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val binding: RegisterBinding = DataBindingUtil.setContentView(this, R.layout.register)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        register_email_register_button.setOnClickListener {
            Tools.hideKeyboard(this)
            viewModel.register()
        }

        viewModel.user.observe(this, {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    startActivity(Intent(this, MainLoggedActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                }

                Resource.Status.PENDING -> {
                    register_email_register_button.transitionToEnd()
                }

                Resource.Status.ERROR -> {
                    register_email_register_button.transitionToStart()
                    register_error_modal.isActive = true
                    register_error_modal.modalInformation = it.message.toString()

                }
            }
        })

        register_register_text.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
    }
}