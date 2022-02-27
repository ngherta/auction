package com.lot.mobile.presentation.features.login

import com.lot.mobile.presentation.MainActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lot.mobile.presentation.infrastructure.extensions.hide
import com.lot.mobile.presentation.infrastructure.extensions.show
import com.lot.mobile.databinding.ActivityLoginBinding
import com.lot.mobile.presentation.features.register.RegisterActivity
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
        binding.registerLink.setOnClickListener{
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
