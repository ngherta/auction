package com.lot.mobile.presentation.features.auctions.preview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lot.mobile.presentation.infrastructure.viewBinding
import com.lot.mobile.R
import com.lot.mobile.databinding.FragmentAuctionPreviewBinding
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
                binding.description.text = auction.description
                binding.status.text = auction.status
                binding.startDate.text = auction.startDate
                binding.finishDate.text = auction.finishDate
                binding.startPrice.text = auction.startPrice.toString() + " $"
                binding.finishPrice.text = auction.finishPrice.toString() + " $"
            }
            it.failure {

            }
        }
    }
}
