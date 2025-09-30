package com.example.flo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.flo.databinding.FragmentArchiveBinding
import com.google.android.material.tabs.TabLayout

class ArchiveFragment : Fragment(R.layout.fragment_archive) {

    private lateinit var binding: FragmentArchiveBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArchiveBinding.bind(view)
        initViews()
        initListeners()
    }

    private fun initViews() = with(binding) {
        // default fragment
        childFragmentManager.beginTransaction().replace(R.id.archiveFragmentContainerView,
            SaveSongFragment()).commit()
    }

    private fun initListeners() = with(binding) {
        tblArchiveIndicator.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        childFragmentManager.beginTransaction().replace(R.id.archiveFragmentContainerView,
                            SaveSongFragment()).commit()
                    }
                    1 -> {
                        childFragmentManager.beginTransaction().replace(R.id.archiveFragmentContainerView,
                            SongFileFragment()).commit()
                    }
                    2 -> {
                        childFragmentManager.beginTransaction().replace(R.id.archiveFragmentContainerView,
                            SaveAlbumFragment()).commit()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

}