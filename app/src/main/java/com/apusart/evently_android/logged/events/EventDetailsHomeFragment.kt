package com.apusart.evently_android.logged.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apusart.api.handleResource
import com.apusart.evently_android.R
import com.apusart.evently_android.databinding.FragmentEventDetailsBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_event_details.*
import kotlinx.android.synthetic.main.leave_event_details.*

class EventDetailsHomeFragment : Fragment() {
    private val viewModel: EventsViewModel by viewModels()
    private val args: EventDetailsHomeFragmentArgs by navArgs()
    private lateinit var binding: FragmentEventDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventId = args.eventHomeId
        viewModel.setEvent(eventId)
        viewModel.event.observe(viewLifecycleOwner, {res ->
            handleResource(res,
                onSuccess = {
                    viewModel.eventDetails = it!!
                    binding.viewModel = viewModel
                    Glide.with(this)
                        .load(if(it.photoPath == "") R.drawable.party_image_template1 else it.photoPath)
                        .into(join_event_cover_photo)
                }, onError = { msg, _ ->
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                })
        })

        viewModel.joiningEvent.observe(viewLifecycleOwner, {res ->
            handleResource(res,
                onSuccess = {
                    join_event_button.transitionToStart()
                    findNavController().popBackStack()
                },
                onPending = {
                    join_event_button.transitionToEnd()
                },
                onError = { _, _ ->
                    join_event_button.transitionToStart()
                })
        })

        join_event_button.setOnClickListener {
            viewModel.joinEvent()
        }
    }
}