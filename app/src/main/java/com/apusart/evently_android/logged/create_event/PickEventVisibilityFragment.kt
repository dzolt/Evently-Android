package com.apusart.evently_android.logged.create_event

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.apusart.evently_android.R
import kotlinx.android.synthetic.main.pick_event_visibility.*

class PickEventVisibilityFragment: Fragment(R.layout.pick_event_visibility) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pick_event_visibility_private.setOnClickListener {
            findNavController().navigate(R.id.createEventActivity)
        }
    }
}