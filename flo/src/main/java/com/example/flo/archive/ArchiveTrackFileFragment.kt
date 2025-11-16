package com.example.flo.archive

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.databinding.FragmentArchiveTrackFileBinding

class ArchiveTrackFileFragment : Fragment(R.layout.fragment_archive_track_file) {

    private lateinit var binding: FragmentArchiveTrackFileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArchiveTrackFileBinding.bind(view)
    }

}