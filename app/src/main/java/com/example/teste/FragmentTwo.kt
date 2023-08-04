package com.example.teste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.teste.databinding.FragmentTwoBinding

class FragmentTwo: Fragment() {

    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTwoBinding.inflate(inflater, container, false)
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
        setUpButton()
    }

    private fun setUpAppbar() {
        binding.appbarLayout.toolbar.apply {
            title = "Fragment Two"
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setNavigationIconTint(
                resources.getColor(
                    R.color.design_default_color_on_primary,
                    requireContext().theme
                )
            )
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpButton() {
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentTwo_to_fragmentError)
        }
    }
}