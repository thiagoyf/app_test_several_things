package com.example.teste

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.teste.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("PERMISSIONS", "Manifest.permission.ACCESS_FINE_LOCATION: true")
            }
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("PERMISSIONS", "Manifest.permission.ACCESS_COARSE_LOCATION: true")
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Log.d("PERMISSIONS", "ALERT - Manifest.permission.ACCESS_FINE_LOCATION")
                AlertDialog.Builder(this)
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
                AlertDialog.Builder(this)
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
}