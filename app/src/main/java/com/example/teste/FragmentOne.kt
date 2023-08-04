package com.example.teste

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
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

    private val permissions by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        else
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        it.forEach { (key, value) ->
            Log.d("PERMISSIONS", "$key: $value")
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
            getPermission()
        }
    }

    private fun getPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("PERMISSIONS", "Manifest.permission.ACCESS_FINE_LOCATION: true")
            }
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("PERMISSIONS", "Manifest.permission.ACCESS_COARSE_LOCATION: true")
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Log.d("PERMISSIONS", "ALERT - Manifest.permission.ACCESS_FINE_LOCATION")
                AlertDialog.Builder(requireContext())
                    .setMessage("ACCESS_FINE_LOCATION")
                    .setPositiveButton("OK", { dialog, which ->
                        Log.d("PERMISSIONS", "Positivo")
                    })
                    .setNegativeButton("CANCEL", { dialog, which ->
                        Log.d("PERMISSIONS", "Negativo")

                    })
                    .show()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                Log.d("PERMISSIONS", "ALERT - Manifest.permission.ACCESS_COARSE_LOCATION")
                AlertDialog.Builder(requireContext())
                    .setMessage("ACCESS_COARSE_LOCATION")
                    .setPositiveButton("OK", { dialog, which ->
                        Log.d("PERMISSIONS", "Positivo")
                    })
                    .setNegativeButton("CANCEL", { dialog, which ->
                        Log.d("PERMISSIONS", "Negativo")
                    })
                    .show()
            }
            else -> {
                requestPermissionLauncher.launch(
                    permissions
                )
            }
        }
    }

    private fun navigateNext() {
        findNavController().navigate(R.id.action_fragmentOne_to_fragmentTwo)
    }
}