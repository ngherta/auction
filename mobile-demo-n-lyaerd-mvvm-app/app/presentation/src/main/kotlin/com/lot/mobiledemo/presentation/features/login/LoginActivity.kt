package com.lot.mobiledemo.presentation.features.login

import com.lot.mobiledemo.presentation.MainActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lot.mobiledemo.presentation.infrastructure.extensions.hide
import com.lot.mobiledemo.presentation.infrastructure.extensions.show
import com.lot.mobiledemo.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val binding by lazy { ActivityLoginBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loginViewModel.authenticationState.observe(this) {
            it.loading { binding.loading.show() }
            it.success {
                binding.loading.hide()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
            it.failure {
                binding.loading.hide()
            }
        }
        binding.loginButton.setOnClickListener {
            loginViewModel.onLogin(
                binding.email.text.toString(),
                binding.passwordInput.binding.password.text.toString()
            )
        }
    }
}
