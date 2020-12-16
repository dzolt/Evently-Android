package com.apusart.evently_android.logged.create_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.apusart.evently_android.R
import com.apusart.evently_android.databinding.AddEventDetailsBinding

class EventDetailsFragment: Fragment() {
    private val viewModel: CreateEventViewModel by activityViewModels()
    private lateinit var binding: AddEventDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_event_details, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}