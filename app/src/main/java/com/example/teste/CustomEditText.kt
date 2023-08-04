package com.example.teste

import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.example.teste.databinding.ViewTextFieldBinding

class CustomEditText @JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewTextFieldBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEditText,
            0, 0
        ).apply {

            try {
                binding.tvLabel.text = getString(R.styleable.CustomEditText_customEditTextLabel)
            } finally {
                recycle()
            }
        }
    }

}