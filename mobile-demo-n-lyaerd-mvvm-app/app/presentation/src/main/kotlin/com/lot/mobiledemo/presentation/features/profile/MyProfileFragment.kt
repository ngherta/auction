package com.lot.mobiledemo.presentation.features.profile

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lot.mobiledemo.R
import com.lot.mobiledemo.databinding.FragmentMyProfileBinding
import com.lot.mobiledemo.presentation.features.profile.settings.SettingAdapter
import com.lot.mobiledemo.presentation.features.profile.settings.SettingItemModel
import com.lot.mobiledemo.presentation.features.profile.settings.SettingsViewModel
import com.lot.mobiledemo.presentation.infrastructure.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {
    private val updateUserViewModel: UpdateUserViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val binding by viewBinding(FragmentMyProfileBinding::bind)
    private val adapter = SettingAdapter(::changeValue)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingsList.adapter = adapter

        settingsViewModel.settings.observe(viewLifecycleOwner) {
            it.success { settings -> adapter.submitList(settings) }
            it.failure {
                // TODO
            }
            it.loading {
                // TODO
            }
        }

        updateUserViewModel.userData.observe(viewLifecycleOwner) {
            it.success {
                e -> run {
                    binding.firstName.setText(e.firstName)
                    binding.lastName.setText(e.lastName)
                    binding.email.setText(e.email)
                }
            }
        }

        binding.updateButton.setOnClickListener{
            updateUserViewModel.onUpdate(
                binding.email.text.toString(),
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
            )
        }

        binding.logout.setOnClickListener{
            updateUserViewModel.logout()
        }

    }

    private fun changeValue(settingItem: SettingItemModel) {

    }
}
