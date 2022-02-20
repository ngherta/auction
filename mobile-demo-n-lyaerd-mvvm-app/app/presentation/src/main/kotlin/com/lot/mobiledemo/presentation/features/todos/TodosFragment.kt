package com.lot.mobiledemo.presentation.features.todos

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lot.mobiledemo.presentation.features.auctions.AuctionsViewModel
import com.lot.mobiledemo.presentation.infrastructure.viewBinding
import com.lot.mobiledemo.R
import com.lot.mobiledemo.databinding.FragmentTodosBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodosFragment : Fragment(R.layout.fragment_todos) {
    private val auctionsViewModel: AuctionsViewModel by viewModels()
    private val binding by viewBinding(FragmentTodosBinding::bind)
}
