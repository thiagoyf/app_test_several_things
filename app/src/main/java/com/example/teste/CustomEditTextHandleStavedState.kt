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

class CustomEditTextHandleStavedState @JvmOverloads
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

    public override fun onSaveInstanceState(): Parcelable? {
        Log.i("SavedState", "onSaveInstanceState")
        return SavedState(super.onSaveInstanceState()).apply {
            val childViewStates = SparseArray<Parcelable>()
            this@CustomEditTextHandleStavedState.children.forEach { child ->
                child.saveHierarchyState(childViewStates)
            }
            childrenStates = childViewStates
        }
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        Log.i("SavedState", "onRestoreInstanceState")
        when (state) {
            is SavedState -> {
                super.onRestoreInstanceState(state.superState)
                state.childrenStates?.let {
                    children.forEach { child -> child.restoreHierarchyState(it) }
                }
            }
            else -> super.onRestoreInstanceState(state)
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    internal class SavedState : BaseSavedState {

        internal var childrenStates: SparseArray<Parcelable>? = null

        constructor(superState: Parcelable?) : super(superState)

        @Suppress("UNCHECKED_CAST")
        constructor(source: Parcel) : super(source) {
            Log.i("SavedState", "Reading children children state from sparse array")
            childrenStates = source.readSparseArray(javaClass.classLoader)
        }

        @Suppress("UNCHECKED_CAST")
        override fun writeToParcel(out: Parcel, flags: Int) {
            Log.i("SavedState", "Writing children state to sparse array")
            super.writeToParcel(out, flags)
            out.writeSparseArray(childrenStates as SparseArray<Any>)
        }

        companion object {
            @Suppress("UNUSED")
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel) = SavedState(source)
                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }

}