package com.apusart.evently_android.guest.login_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.apusart.api.Resource
import com.apusart.appComponent
import com.apusart.evently_android.R
import com.apusart.evently_android.databinding.LoginBinding
import com.apusart.evently_android.guest.register_activity.RegisterActivity
import com.apusart.evently_android.logged.main.MainLoggedActivity
import com.apusart.tools.Tools
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.view.*
import kotlinx.android.synthetic.main.register.*
import javax.inject.Inject

class LoginActivity: AppCompatActivity() {
    @Inject
    lateinit var viewModel: LoginActivityViewModel

    private val RC_SIGN_IN = 123

    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val binding: LoginBinding = DataBindingUtil.setContentView(this, R.layout.login)
        binding.viewModel = viewModel

        login_email_login_button.setOnClickListener {
            Tools.hideKeyboard(this)
            viewModel.logIn()
        }

        login_register_text.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }

        viewModel.user.observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    startActivity(
                        Intent(this, MainLoggedActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    )
                }

                Resource.Status.PENDING -> {
                    login_email_login_button.transitionToEnd()
                }

                Resource.Status.ERROR -> {
                    login_email_login_button.transitionToStart()
                    login_error_modal.isActive = true
                    login_error_modal.modalInformation = it.message.toString()

                }
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        login_email_google_button.setOnClickListener {
            googleSignIn()
        }
    }

    private fun googleSignIn() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val creadential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(creadential)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    startActivity(Intent(this, MainLoggedActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                } else {
                    login_error_modal.isActive = true
                    login_error_modal.modalInformation = it.exception.toString()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken!!)
            } catch(e: ApiException) {
                login_error_modal.isActive = true
                login_error_modal.modalInformation = e.message.toString()
            }
        }
    }
}