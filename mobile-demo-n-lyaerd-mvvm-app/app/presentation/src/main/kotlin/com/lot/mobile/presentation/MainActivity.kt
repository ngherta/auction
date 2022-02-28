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
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.lot.mobile.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.platform.Platform.Companion.INFO
import java.util.logging.Level.INFO

@AndroidEntryPoint
class MainActivity : SecondaryFragmentHandler, AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val fragments = listOf(
        PagerFragment(AuctionsFragment(), R.string.title_auctions, R.drawable.ic_article),
        PagerFragment(AlbumsFragment(), R.string.title_albums, R.drawable.ic_photo_album),
        PagerFragment(MyProfileFragment(), R.string.my_profile, R.drawable.ic_checklist)
    )



    private val isNetworkAvailable: Boolean
        get() {
            Log.i("","HELLO-NGH5")
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                Log.i("","HELLO-NGH3")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    // Returns a Network object corresponding to
                    // the currently active default data network.
                    val network = connectivityManager.activeNetwork ?: return false

                    // Representation of the capabilities of an active network.
                    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

                    return when {
                        // Indicates this network uses a Wi-Fi transport,
                        // or WiFi has network connectivity
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                        // Indicates this network uses a Cellular transport. or
                        // Cellular has network connectivity
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                        // else return false
                        else -> false
                    }
                } else {
                    // if the android version is below M
                    @Suppress("DEPRECATION") val networkInfo =
                        connectivityManager.activeNetworkInfo ?: return false
                    @Suppress("DEPRECATION")
                    return networkInfo.isConnected
                }
            }
            return false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureViewPager()
        popup()
    }

    private fun popup() {
        if (!isNetworkAvailable) {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Internet Connection Alert")
                .setMessage("Please Check Your Internet Connection")
                .setPositiveButton("retry") { _, q ->popup()}
                .setNegativeButton(
                    "Close"
                ) { dialogInterface, i -> finish() }.show()
        } else if (isNetworkAvailable) {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Success")
                .setMessage("test Connection")
                .setPositiveButton("retry") { _, q ->popup()}
                .setNegativeButton(
                    "Close"
                ) { dialogInterface, i -> finish() }.show()
        }
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
