package com.apusart.components

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.apusart.evently_android.R
import kotlinx.android.synthetic.main.main_input.view.*

class MainInput(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val view = LayoutInflater.from(context)
        .inflate(R.layout.main_input, this, false)

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.MainInput,
            0, 0
        ).apply {
            val title = getText(R.styleable.MainInput_main_input_input_title)
            val imeOptions = getInt(R.styleable.MainInput_android_imeOptions, 0)
            val inputType = getInt(R.styleable.MainInput_android_inputType, 0)

            view.main_input_title.text = title
            view.main_input_edit_text.imeOptions = imeOptions
            view.main_input_edit_text.inputType = inputType

        }

        addView(view)
    }

    companion object {
        @InverseBindingAdapter(attribute = "app:main_input_input", event = "app:main_input_inputAttrChanged")
        @JvmStatic fun setInput(view: MainInput): String {
            return view.view.main_input_edit_text.text.toString()
        }

        @BindingAdapter("app:main_input_inputAttrChanged")
        @JvmStatic fun onInputAttributeChanged(view: MainInput, attrChange: InverseBindingListener?) {

            view.main_input_edit_text.doOnTextChanged { text, start, before, count ->
                attrChange ?: return@doOnTextChanged
                attrChange.onChange()
            }

        }

        @BindingAdapter("app:main_input_input")
        @JvmStatic fun titleChanged(view: MainInput, value: String?) {
            value ?: return
            if (view.view.main_input_edit_text.text.toString() == value)
                return
            view.view.main_input_edit_text.setText(value)
        }
    }
}


