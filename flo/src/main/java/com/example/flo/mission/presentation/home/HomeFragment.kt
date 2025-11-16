package com.example.flo.mission.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.R
import com.example.flo.mission.model.TrackModel
import com.example.flo.databinding.FragmentHomeBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initViews()
        initListeners()

    }

    private fun initViews() = with(binding) {
        viewPager2MusicBanner.adapter = HomeBannerAdapter(this@HomeFragment)
        circleIndicator.setViewPager(viewPager2MusicBanner)
        val totalPageCount = viewPager2MusicBanner.adapter?.itemCount ?: 0 // 3
        var currentPage = viewPager2MusicBanner.currentItem // 0
        job = lifecycleScope.launch {
            while (isActive) {
                delay(1500)
                val nextPage = (currentPage + 1) % totalPageCount
                viewPager2MusicBanner.setCurrentItem(nextPage, true)
                circleIndicator.animatePageSelected(nextPage)
                currentPage = nextPage
            }
        }

        val testTrackList = mutableListOf(
            TrackModel(
                trackTitle = "Starry Night",
                trackArtist = "Vincent",
                trackThumbnail = R.drawable.img_track_sample_one
            ),
            TrackModel(
                trackTitle = "Bohemian Rhapsody",
                trackArtist = "Queen",
                trackThumbnail = R.drawable.img_track_sample_two
            ),
            TrackModel(
                trackTitle = "Imagine",
                trackArtist = "John Lennon",
                trackThumbnail = R.drawable.img_track_sample_three
            ),
            TrackModel(
                trackTitle = "Havana",
                trackArtist = "Camila Cabello",
                trackThumbnail = R.drawable.img_track_sample_four
            ),
            TrackModel(
                trackTitle = "Shape of You",
                trackArtist = "Ed Sheeran",
                trackThumbnail = R.drawable.img_track_sample_five
            ),
            TrackModel(
                trackTitle = "Someone Like You",
                trackArtist = "Adele",
                trackThumbnail = R.drawable.img_track_sample_six
            )
        )
        val trackAdapter = HomeTrackAdapter(requireContext(), testTrackList) { track ->
            val action = HomeFragmentDirections.actionHomeFragmentToAlbumFragment(track)
            findNavController().navigate(action)
            activity?.findViewById<TextView>(R.id.tvTrackTitle)?.text = track.trackTitle
            activity?.findViewById<TextView>(R.id.tvTrackArtist)?.text = track.trackArtist
        }
        recyclerviewHome.adapter = trackAdapter
        recyclerviewHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initListeners() = with(binding) {

    }

}