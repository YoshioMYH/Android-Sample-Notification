package com.sample.notificationapplication

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val extras = intent.extras
        extras?.let {
            val link = extras.getString("link")
            link?.let {
                Log.d(TAG, "Link: $link")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }
        }

        checkNotificationsPermission()
    }

    private fun checkNotificationsPermission() {
        val action = { }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionsHelper.requestPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
                // Nenhum comportamento caso a permissão seja aceita
                behaviourGranted = action,
                // Nenhum comportamento caso a permissão seja negada
                behaviourNotGranted = action,
                // Nenhuma justificativa caso a permissão tenha sido negada anteriormente
                rationale = action,
            )
        } else {
            action()
        }
    }
}
