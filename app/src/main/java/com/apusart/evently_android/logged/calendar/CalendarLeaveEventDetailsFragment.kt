package com.apusart.evently_android.logged.calendar

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
import com.apusart.evently_android.databinding.LeaveEventDetailsBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.event_list_item.view.*
import kotlinx.android.synthetic.main.leave_event_details.*

class CalendarLeaveEventDetailsFragment : Fragment() {
    private val viewModel: CalendarLeaveEventViewModel by viewModels()
    private val args: CalendarLeaveEventDetailsFragmentArgs by navArgs()
    private lateinit var binding: LeaveEventDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.leave_event_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventId = args.eventId
        viewModel.setEvent(eventId)
        viewModel.event.observe(viewLifecycleOwner, {res ->
            handleResource(res,
                onSuccess = {
                    viewModel.eventDetails = it!!
                    binding.viewModel = viewModel
                    Glide.with(this)
                        .load(if(it.photoPath == "") R.drawable.party_image_template1 else it.photoPath)
                        .into(leave_event_cover_photo)
                }, onError = { msg, _ ->
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                })
        })

        viewModel.leavingEvent.observe(viewLifecycleOwner, {res ->
            handleResource(res,
                onSuccess = {
                    leave_event_button.transitionToStart()
                    findNavController().popBackStack()
                },
                onPending = {
                    leave_event_button.transitionToEnd()
                },
                onError = { _, _ ->
                    leave_event_button.transitionToStart()
                })
        })

        binding.leaveEventButton.setOnClickListener {
            viewModel.leaveEvent()
        }

    }

}