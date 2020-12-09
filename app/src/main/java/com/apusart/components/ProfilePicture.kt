package com.apusart.components

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.apusart.evently_android.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.profile_picture.view.*

class ProfilePicture(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    private val view = LayoutInflater.from(context)
        .inflate(R.layout.profile_picture, this, false)

    init {
        setOnClickListener {
            view.profile_picture_change_picture_button.isVisible = true
            view.profile_picture_change_picture_button.requestFocus()
        }

        view.profile_picture_cancel_change_picture_button.setOnClickListener {
            view.profile_picture_change_picture_button.isVisible = false
        }

        addView(view)
    }

    fun loadPhoto(url: String?) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .into(view.profile_picture_picture)
    }

    fun loadPhoto(uri: Uri?) {
        Glide.with(this)
            .load(uri)
            .circleCrop()
            .into(view.profile_picture_picture)
    }

    fun setOnChangePictureListener(l: ((View) -> Unit)?) {
        val x = { view: View ->
            l?.invoke(view)
            view.profile_picture_change_picture_button.isVisible = false
        }
        view.profile_picture_change_picture_button.setOnClickListener(x)
    }


}