package com.lot.mobiledemo.presentation.features.auctions.preview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lot.mobiledemo.presentation.infrastructure.viewBinding
import com.lot.mobiledemo.R
import com.lot.mobiledemo.databinding.FragmentAuctionPreviewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

data class AuctionPreviewInput(val auctionId: Int) : Serializable

@AndroidEntryPoint
class AuctionPreviewFragment : Fragment(R.layout.fragment_auction_preview) {
    private val binding by viewBinding(FragmentAuctionPreviewBinding::bind)
    private val viewModel: AuctionPreviewViewModel by viewModels()

    companion object {
        fun newInstance(input: AuctionPreviewInput): AuctionPreviewFragment {
            val fragment = AuctionPreviewFragment()
            val args = Bundle()
            args.putSerializable("input", input)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val input = requireArguments().getSerializable("input") as AuctionPreviewInput
        viewModel.loadData(input.auctionId)
        viewModel.auctionPreview.observe(viewLifecycleOwner) {
            it.loading {

            }
            it.success { auction ->
                binding.title.text = auction.title
                binding.body.text = auction.body
            }
            it.failure {

            }
        }
    }
}
