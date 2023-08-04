package com.example.teste

import android.content.Intent
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.teste.databinding.FragmentOneBinding

class FragmentOne : Fragment() {

    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!

    private var animationA: AnimatedVectorDrawable? = null

    private lateinit var animationB: AnimatedVectorDrawable

    private val animationCallback = object: Animatable2.AnimationCallback() {
        override fun onAnimationEnd(drawable: Drawable?) {
            super.onAnimationEnd(drawable)
            animationA?.start()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOneBinding.inflate(inflater, container, false)
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

        val constraintLayout = binding.root

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.constrainPercentWidth(binding.customProgress.id, 0.3f)
        constraintSet.setHorizontalBias(binding.customProgress.id, 0f)

        constraintSet.applyTo(constraintLayout)


        binding.imageA.apply {
            setBackgroundResource(R.drawable.a_1)
            animationA = background as AnimatedVectorDrawable
        }
        binding.imageB.apply {
            setBackgroundResource(R.drawable.b)
            animationB = background as AnimatedVectorDrawable
        }

        animationA?.registerAnimationCallback(animationCallback)

        Glide
            .with(requireContext())
            .load("https://meualelo-recommendations.hml.sao.siteteste.inf.br/2022/10/26/6359bc3e11a733431365e702@2x.png")
            .into(binding.imageGlide)

    }

    private fun setUpAppbar() {
        binding.appbarLayout.toolbar.apply {
            title = "Fragment One"
        }
    }

    private fun setUpButton() {
        binding.nextButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=S4DaLY2DkUg"))
            startActivity(intent)
        }
    }

    private fun navigateNext() {
        findNavController().navigate(R.id.action_fragmentOne_to_fragmentTwo)
    }
}