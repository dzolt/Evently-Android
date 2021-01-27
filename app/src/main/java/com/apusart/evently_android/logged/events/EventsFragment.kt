package com.apusart.evently_android.logged.events

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.apusart.api.handleResource
import com.apusart.evently_android.R
import kotlinx.android.synthetic.main.events_fragment.*

class EventsFragment : Fragment(R.layout.events_fragment) {

    private val viewModel: EventsViewModel by viewModels()
    private lateinit var eventsAdapter: EventsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsAdapter = EventsAdapter()

        events_fragment_list.apply {
            adapter = eventsAdapter
        }
        setUpObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshEvents()
    }

    private fun setUpObservers() {
        viewModel.events.observe(viewLifecycleOwner, { res ->
            handleResource(res,
                onSuccess = {
                    eventsAdapter.submitList(it)
                }, onError = { msg, _ ->
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                })
        })
    }
}