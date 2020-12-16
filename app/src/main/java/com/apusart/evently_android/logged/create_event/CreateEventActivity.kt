package com.apusart.evently_android.logged.create_event

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.apusart.evently_android.EventPageDestination
import com.apusart.evently_android.R
import com.apusart.evently_android.databinding.CreateEventActivityBinding
import kotlinx.android.synthetic.main.create_event_activity.*

//data class musi być bo nazwa headera, nazwa buttona i aktualna destynacja



class CreateEventActivity: AppCompatActivity() {
    private val destinations = listOf(
        EventPageDestination("Event Details", "Step 1", R.id.AddEventDetailsFragment, "Next"),
        EventPageDestination("Place and time", "Step 2", R.id.AddEventPlaceTimeFragment, "Next"),
        EventPageDestination("Category", "Step 3", R.id.AddEventPickCategoryFragment, "Next"))

    val viewModel: CreateEventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: CreateEventActivityBinding = DataBindingUtil.setContentView(this, R.layout.create_event_activity)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.currentPage.observe(this, {
            val destination = destinations[it]

            findNavController(R.id.create_event_activity_fragment_container).navigate(destinations[it].destination)
            create_event_activity_header.title = destination.title
            create_event_activity_header.subtitle = destination.subtitle
            create_event_activity_button_next.text = destination.buttonText

        })

        create_event_activity_button_next.setOnClickListener {
            viewModel.increment()
        }

        create_event_activity_header.setOnLeadingIconClickListener {
            findNavController(R.id.create_event_activity_fragment_container).popBackStack()
            viewModel.decrement()
        }
    }


}