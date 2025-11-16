package com.example.flo.mission.presentation.archive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.flo.R
import com.example.flo.databinding.FragmentArchiveBinding
import com.google.android.material.tabs.TabLayoutMediator

class ArchiveFragment : Fragment(R.layout.fragment_archive) {

    private lateinit var binding: FragmentArchiveBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArchiveBinding.bind(view)
        initViews()
        initListeners()
    }

    private fun initViews() = with(binding) {
        // ViewPager2 Adapter 연결
        archiveViewPager2.adapter = ArchiveAdapter(this@ArchiveFragment)

        // TabLayout 과 ViewPager2 연동
        TabLayoutMediator(tblArchiveIndicator, archiveViewPager2) { tab, position ->
            tab.text = when(position) {
                0 -> "저장한 곡"
                1 -> "음악파일"
                2 -> "저장앨범"
                else -> ""
            }
        }.attach()
    }

    private fun initListeners() = with(binding) {

    }

}