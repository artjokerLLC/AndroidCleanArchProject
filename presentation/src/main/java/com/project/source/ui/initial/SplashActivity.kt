package com.project.source.ui.initial

import android.support.v7.app.AppCompatActivity
import com.project.source.ui.dashboard.DashboardActivity

class SplashActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        startActivity(DashboardActivity.newIntent(this))
        finish()
    }
}
