package com.example.teste

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.teste.databinding.FragmentErrorBinding
import com.example.teste.databinding.FragmentTwoBinding

class FragmentError : Fragment() {

    private var _binding: FragmentErrorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpView() {
        setUpAppbar()
    }

    private fun setUpAppbar() {
        binding.toolbar.root.apply {
            val icon = ResourcesCompat
                .getDrawable(resources, R.drawable.ic_baseline_close_24, requireContext().theme)
            if (icon != null) {
                icon.colorFilter = PorterDuffColorFilter(
                    resources.getColor(
                        R.color.design_default_color_primary,
                        requireContext().theme
                    ), PorterDuff.Mode.SRC_IN
                )
            }
            navigationIcon = icon
            setNavigationOnClickListener {
                findNavController().popBackStack(
                    R.id.fragmentOne,
                    inclusive = false
                )
            }
        }
    }
}