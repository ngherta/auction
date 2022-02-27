package com.lot.mobiledemo.presentation.features.auctions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lot.mobiledemo.presentation.SecondaryFragmentHandler
import com.lot.mobiledemo.presentation.features.auctions.list.AuctionsAdapter
import com.lot.mobiledemo.presentation.features.auctions.preview.AuctionPreviewFragment
import com.lot.mobiledemo.presentation.features.auctions.preview.AuctionPreviewInput
import com.lot.mobiledemo.presentation.infrastructure.viewBinding
import com.lot.mobiledemo.R
import com.lot.mobiledemo.databinding.FragmentAuctionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuctionsFragment : Fragment(R.layout.fragment_auctions) {
    private val auctionsViewModel: AuctionsViewModel by viewModels()
    private val binding by viewBinding(FragmentAuctionsBinding::bind)
    private val adapter = AuctionsAdapter(::openAuctionPreview)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.auctionsList.adapter = adapter
        auctionsViewModel.auctions.observe(viewLifecycleOwner) {
            it.success { auctions -> adapter.submitList(auctions) }
            it.failure {
                // TODO
            }
            it.loading {
                // TODO
            }
        }
    }

    private fun openAuctionPreview(auctionItem: AuctionItemModel) {
        (requireActivity() as SecondaryFragmentHandler).displaySecondaryFragment(
            AuctionPreviewFragment.newInstance(AuctionPreviewInput(auctionItem.id))
        )
    }
}
