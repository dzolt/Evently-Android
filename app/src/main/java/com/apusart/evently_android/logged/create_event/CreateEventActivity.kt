package com.apusart.evently_android.logged.create_event

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.apusart.evently_android.R
import com.apusart.evently_android.databinding.CreateEventActivityBinding
import kotlinx.android.synthetic.main.create_event_activity.*

class CreateEventActivity: AppCompatActivity() {
    val viewModel: CreateEventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: CreateEventActivityBinding = DataBindingUtil.setContentView(this, R.layout.create_event_activity)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        create_event_activity_button_next.setOnClickListener {
            viewModel.add()
        }
    }

}