package com.example.flo.mission.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.R
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.mission.database.DatabaseModule
import com.example.flo.mission.presentation.MusicViewModel
import com.example.flo.mission.presentation.MusicViewModelFactory
import kotlinx.coroutines.Job

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private var job: Job? = null

    private val musicViewModel: MusicViewModel by activityViewModels {
        MusicViewModelFactory(albumDao = DatabaseModule.albumDao, songDao = DatabaseModule.songDao)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initViews()
        initObservers()
    }

    private fun initViews() = with(binding) {
        viewPager2MusicBanner.adapter = HomeBannerAdapter(this@HomeFragment)
        circleIndicator.setViewPager(viewPager2MusicBanner)
//        val totalPageCount = viewPager2MusicBanner.adapter?.itemCount ?: 0 // 3
//        var currentPage = viewPager2MusicBanner.currentItem // 0
//        job = lifecycleScope.launch {
//            while (isActive) {
//                delay(1500)
//                val nextPage = (currentPage + 1) % totalPageCount
//                viewPager2MusicBanner.setCurrentItem(nextPage, true)
//                circleIndicator.animatePageSelected(nextPage)
//                currentPage = nextPage
//            }
//        }
    }

    private fun initObservers() = with(binding) {
        musicViewModel.albumResources.observe(viewLifecycleOwner) { albumList ->
            Log.e("TEST", "" + albumList)
            val albumAdapter = HomeAlbumAdapter(requireContext(), albumList) { album ->
                val action = HomeFragmentDirections.actionHomeFragmentToAlbumFragment(album)
                findNavController().navigate(action)
                activity?.findViewById<TextView>(R.id.tvTrackTitle)?.text = album.title
                activity?.findViewById<TextView>(R.id.tvTrackArtist)?.text = album.artist
            }
            recyclerviewHome.adapter = albumAdapter
            recyclerviewHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}