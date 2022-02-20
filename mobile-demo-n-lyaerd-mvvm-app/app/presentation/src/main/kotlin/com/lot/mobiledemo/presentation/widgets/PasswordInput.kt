package com.lot.mobiledemo.presentation.widgets

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.lot.mobiledemo.databinding.LayoutPasswordInputBinding

class PasswordInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    val binding = LayoutPasswordInputBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.showPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.password.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.password.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }
}
