package com.example.weatherapplication.presentation.screens

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.common.constants.Constant

@Composable
fun HandleLocationPermissions(
    context: Context,
    permissions: Array<String>,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        permissionMap ->
        val areGranted = permissionMap.values.reduce { accepted, next ->
            accepted && next
        }
        if (areGranted) {
            onPermissionGranted()
        }
        else {
            onPermissionDenied()
        }
    }
    LaunchedEffect(Unit) {
        if (permissions.all {
            ContextCompat.checkSelfPermission(context , it) == PackageManager.PERMISSION_GRANTED
            }) {
            onPermissionGranted()
        } else {
            Constant.permissions.forEach { permission ->
                Toast.makeText(
                    context,
                    "Location permission required for the app to function, Please grant permission to continue",
                    Toast.LENGTH_LONG
                ).show()

                AlertDialog.Builder(context)
                    .setTitle("Permission Required")
                    .setMessage("Location permission is required to fetch weather information.")
                    .setPositiveButton("OK") { _, _ ->
                        launcher.launch(Constant.permissions)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }.show()
            }
        }
    }
}