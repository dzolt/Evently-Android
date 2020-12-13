package com.apusart.evently_android.logged.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.apusart.evently_android.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main_logged.*

//viewModel.user.observe(this, Observer {
//    text_test1.text =
//        "${it?.email ?: "brak emailu"} ${it?.isAnonymous ?: "normalny anonimus"}"
//})
//
//val googleSignInClient = GoogleSignIn.getClient(applicationContext, LoginTools.gso)
//logout_button.setOnClickListener {
//    Firebase.auth.signOut()
//    LoginManager.getInstance().logOut()
//    googleSignInClient.signOut()
//
//    startActivity(
//        Intent(this, InitialActivity::class.java)
//            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//    )
//    finishAffinity()
//}

class TestViewModel: ViewModel() {
    val user = MutableLiveData(Firebase.auth.currentUser)
}

class MainLoggedActivity: AppCompatActivity(R.layout.main_logged) {
    val viewModel: TestViewModel by viewModels()

    override fun onBackPressed() {
        val navController = findNavController(R.id.main_logged_fragment_container)
        when(navController.currentDestination?.id) {
            R.id.eventsFragment -> {
                val startMain = Intent(Intent.ACTION_MAIN)
                startMain.addCategory(Intent.CATEGORY_HOME)
                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(startMain)
            }
            R.id.search_page -> {
                main_logged_navigation_view.selectedItemId = R.id.events_page
            }
            R.id.add_event_page -> {
                main_logged_navigation_view.selectedItemId = R.id.events_page
            }
            R.id.calendarFragment -> {
                main_logged_navigation_view.selectedItemId = R.id.events_page
            }
            R.id.profileFragment -> {
                main_logged_navigation_view.selectedItemId = R.id.events_page
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        main_logged_navigation_view.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.events_page -> {
                    findNavController(R.id.main_logged_fragment_container)
                        .navigate(R.id.eventsFragment)
                    true
                }
                R.id.search_page -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.add_event_page -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.calendar_page -> {
                    findNavController(R.id.main_logged_fragment_container)
                        .navigate(R.id.calendarFragment)
                    true
                }
                R.id.profile_page -> {
                    // Respond to navigation item 2 click
                    findNavController(R.id.main_logged_fragment_container)
                        .navigate(R.id.profileFragment)
                    true
                }

                else -> false
            }
        }


    }
}