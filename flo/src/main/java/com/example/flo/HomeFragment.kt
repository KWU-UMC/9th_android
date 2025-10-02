package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initViews()
        initListeners()

    }

    private fun initViews() = with(binding) {
        viewPager2MusicBanner.adapter = HomeAdapter(this@HomeFragment)
        // viewpager2 와 indicator 연동하기
        dotsIndicator.attachTo(viewPager2MusicBanner)
    }

    private fun initListeners() = with(binding) {
        llAlbumOne.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAlbumFragment(AlbumModel(
                title = tvTodayReleasedAlbumOneTitle.text.toString(),
                singer = tvTodayReleasedAlbumOneSinger.text.toString(),
                thumbnail = R.drawable.img_album_today_released_1
            ))
            findNavController().navigate(action)
        }
    }

}