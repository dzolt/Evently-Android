package com.apusart.evently_android.logged.calendar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.apusart.api.handleResource
import com.apusart.evently_android.R
import com.apusart.evently_android.logged.events.EventsAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.calendar_fragment.*
import kotlinx.android.synthetic.main.events_fragment.*

class CalendarFragment : Fragment(R.layout.calendar_fragment) {
    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var calendarAdapter: CalendarAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarAdapter = CalendarAdapter()

        calendar_fragment_list.apply {
            adapter = calendarAdapter
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.events.observe(viewLifecycleOwner, { res ->
            handleResource(res,
                onSuccess = {
                    calendarAdapter.submitList(it)
                }, onError = { msg, _ ->
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                })
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateEventList()
    }
}