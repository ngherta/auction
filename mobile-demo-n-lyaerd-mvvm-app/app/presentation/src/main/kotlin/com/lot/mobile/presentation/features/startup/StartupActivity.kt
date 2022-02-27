package com.lot.mobile.presentation.features.startup

import com.lot.mobile.presentation.MainActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.lot.mobile.presentation.features.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartupActivity : AppCompatActivity() {
    private val startupViewModel: StartupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }
        startupViewModel.userDataLoadingState.observe(this) {
            finish()
            it.success { startActivity(Intent(this, MainActivity::class.java)) }
            it.failure { startActivity(Intent(this, LoginActivity::class.java)) }
        }
    }
}
