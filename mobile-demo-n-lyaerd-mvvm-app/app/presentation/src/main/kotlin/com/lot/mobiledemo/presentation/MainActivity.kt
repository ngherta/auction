package com.lot.mobiledemo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lot.mobiledemo.presentation.features.albums.AlbumsFragment
import com.lot.mobiledemo.presentation.features.auctions.AuctionsFragment
import com.lot.mobiledemo.presentation.features.todos.TodosFragment
import com.lot.mobiledemo.presentation.infrastructure.pager.FragmentPagerAdapter
import com.lot.mobiledemo.presentation.infrastructure.pager.PagerFragment
import com.lot.mobiledemo.R

import com.lot.mobiledemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : SecondaryFragmentHandler, AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val fragments = listOf(
        PagerFragment(AuctionsFragment(), R.string.title_auctions, R.drawable.ic_article),
        PagerFragment(AlbumsFragment(), R.string.title_albums, R.drawable.ic_photo_album),
        PagerFragment(TodosFragment(), R.string.title_todos, R.drawable.ic_checklist)
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
