package com.example.flo.mission.presentation.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.databinding.FragmentExploreBinding


class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private lateinit var binding: FragmentExploreBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)
    }

}