package com.lot.mobile.presentation.features.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lot.mobile.databinding.ActivityRegisterBinding
import com.lot.mobile.presentation.features.login.LoginActivity
import com.lot.mobile.presentation.infrastructure.extensions.hide
import com.lot.mobile.presentation.infrastructure.extensions.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val registerViewModel: RegisterViewModel by viewModels()
    private val binding by lazy { ActivityRegisterBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        registerViewModel.registerState.observe(this) {
            it.loading { binding.loading.show() }
            it.success {
                binding.loading.hide()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            it.failure {
                binding.loading.hide()
            }
        }
        binding.registerButton.setOnClickListener {
            registerViewModel.onRegister(
                binding.email.text.toString(),
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.passwordInput.binding.password.text.toString()
            )
        }
        binding.loginLink.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
