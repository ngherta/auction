package com.lot.mobiledemo.presentation.features.albums

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lot.mobiledemo.presentation.features.auctions.AuctionsViewModel
import com.lot.mobiledemo.presentation.infrastructure.viewBinding
import com.lot.mobiledemo.R
import com.lot.mobiledemo.databinding.FragmentAlbumsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : Fragment(R.layout.fragment_albums) {
    private val auctionsViewModel: AuctionsViewModel by viewModels()
    private val binding by viewBinding(FragmentAlbumsBinding::bind)
}
