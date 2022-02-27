package com.lot.mobile.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lot.mobile.presentation.features.albums.AlbumsFragment
import com.lot.mobile.presentation.features.auctions.AuctionsFragment
import com.lot.mobile.presentation.features.profile.MyProfileFragment
import com.lot.mobile.presentation.infrastructure.pager.FragmentPagerAdapter
import com.lot.mobile.presentation.infrastructure.pager.PagerFragment
import com.lot.mobile.R

import com.lot.mobile.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : SecondaryFragmentHandler, AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val fragments = listOf(
        PagerFragment(AuctionsFragment(), R.string.title_auctions, R.drawable.ic_article),
        PagerFragment(AlbumsFragment(), R.string.title_albums, R.drawable.ic_photo_album),
        PagerFragment(MyProfileFragment(), R.string.my_profile, R.drawable.ic_checklist)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureViewPager()
    }

    override fun displaySecondaryFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack("secondary")
            .replace(R.id.secondary_fragment_container, fragment)
            .commit()
    }

    private fun configureViewPager() {
        binding.viewPager.adapter = FragmentPagerAdapter(
            supportFragmentManager,
            lifecycle,
            fragments
        )
        binding.viewPager.offscreenPageLimit = 3
        TabLayoutMediator(binding.navigationBar, binding.viewPager) { tab, position ->
            tab.setText(fragments[position].nameRes)
            tab.setIcon(fragments[position].iconRes)
        }.attach()
    }
}
