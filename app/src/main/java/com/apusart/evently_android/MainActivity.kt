package com.apusart.evently_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apusart.components.TestUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.event_list_item.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}