package com.lot.mobile.presentation.features.albums

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lot.mobile.presentation.features.auctions.AuctionsViewModel
import com.lot.mobile.presentation.infrastructure.viewBinding
import com.lot.mobile.R
import com.lot.mobile.databinding.FragmentAlbumsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : Fragment(R.layout.fragment_albums) {
    private val auctionsViewModel: AuctionsViewModel by viewModels()
    private val binding by viewBinding(FragmentAlbumsBinding::bind)
}
