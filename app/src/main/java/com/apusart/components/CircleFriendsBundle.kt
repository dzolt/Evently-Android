package com.apusart.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.apusart.evently_android.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.participants_number.view.*

class TestUser {}

class Participants(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    private val MAX_ITEMS = 3
    var listOfParticipants: List<TestUser>? = listOf()
        set(value) {
            if(value == null || value.isEmpty())
                field = value
            else
                setupView(value)
            requestLayout()
        }

    class ParticipantImage(context: Context): AppCompatImageView(context) {
        var image: String? = null
            set(value) {
                if (value == null)
                    field = value
                else
                    Glide.with(this)
                        .load(value)
                        .circleCrop()
                        .into(this)
            }

        init {
            id = View.generateViewId()
        }
    }

    private fun setupView(list: List<TestUser>) {
        val listOfImages = list.subList(0, if(list.size > MAX_ITEMS) MAX_ITEMS else list.size).map {
            ParticipantImage(context)
        }

        listOfImages.forEachIndexed { index, participantImage ->
            val params = LayoutParams(
                resources.getDimension(R.dimen._32dp).toInt(),
                resources.getDimension(R.dimen._32dp).toInt())

            participantImage.image = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png"

            if (index == 0)
                params.topToTop = 0
            else {
                params.topToBottom = listOfImages[index - 1].id
                params.bottomToBottom = listOfImages[index - 1].id
            }

            participantImage.layoutParams = params
            addView(participantImage, params)
        }

        if (list.size > MAX_ITEMS) {
            val params = LayoutParams(
                resources.getDimension(R.dimen._32dp).toInt(),
                resources.getDimension(R.dimen._32dp).toInt())
            val view = LayoutInflater.from(context)
                .inflate(R.layout.participants_number, this, false)

            params.topToBottom = listOfImages.last().id
            params.bottomToBottom = listOfImages.last().id

            view.participants_number.text = resources.getString(R.string.additional_participants, list.size - listOfImages.size)
            view.layoutParams = params
            addView(view, params)
        }
    }


}