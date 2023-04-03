package com.sample.notificationapplication

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PermissionsHelper {
    companion object {
        fun requestPermission(
            activity: AppCompatActivity,
            permission: String,
            behaviourGranted: () -> Unit,
            behaviourNotGranted: () -> Unit,
            rationale: () -> Unit,
        ) {
            val requestPermissionLauncher = activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
            ) { isGranted: Boolean ->
                if (isGranted) {
                    behaviourGranted()
                } else {
                    behaviourNotGranted()
                }
            }

            when {
                ContextCompat.checkSelfPermission(
                    activity,
                    permission,
                ) == PackageManager.PERMISSION_GRANTED -> {
                    behaviourGranted()
                }
                activity.shouldShowRequestPermissionRationale(permission) -> {
                    rationale()
                }
                else -> {
                    requestPermissionLauncher.launch(permission)
                }
            }
        }
    }
}
