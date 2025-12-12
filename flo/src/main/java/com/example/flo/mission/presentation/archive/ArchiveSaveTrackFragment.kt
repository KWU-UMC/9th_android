package com.example.flo.mission.presentation.archive

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.R
import com.example.flo.mission.domain.model.Track
import com.example.flo.databinding.FragmentArchiveSaveTrackBinding

class ArchiveSaveTrackFragment : Fragment(R.layout.fragment_archive_save_track) {

    private lateinit var binding: FragmentArchiveSaveTrackBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArchiveSaveTrackBinding.bind(view)
        initViews()
    }

    private fun initViews() = with(binding) {
        val testArchiveSaveTrackList = mutableListOf(
            Track(
                trackTitle = "Starry Night",
                trackArtist = "Vincent",
                trackThumbnail = R.drawable.img_track_sample_one
            ),
            Track(
                trackTitle = "Bohemian Rhapsody",
                trackArtist = "Queen",
                trackThumbnail = R.drawable.img_track_sample_two
            ),
            Track(
                trackTitle = "Imagine",
                trackArtist = "John Lennon",
                trackThumbnail = R.drawable.img_track_sample_three
            ),
            Track(
                trackTitle = "Havana",
                trackArtist = "Camila Cabello",
                trackThumbnail = R.drawable.img_track_sample_four
            ),
            Track(
                trackTitle = "Shape of You",
                trackArtist = "Ed Sheeran",
                trackThumbnail = R.drawable.img_track_sample_five
            ),
            Track(
                trackTitle = "Someone Like You",
                trackArtist = "Adele",
                trackThumbnail = R.drawable.img_track_sample_six
            ),
            Track(
                trackTitle = "Blinding Lights",
                trackArtist = "The Weekend",
                trackThumbnail = R.drawable.img_track_sample_one
            ),
            Track(
                trackTitle = "Good 4 u",
                trackArtist = "Olivia Rodrigo",
                trackThumbnail = R.drawable.img_track_sample_two
            ),
            Track(
                trackTitle = "Levitating",
                trackArtist = "Dua Lipa",
                trackThumbnail = R.drawable.img_track_sample_three
            ),
            Track(
                trackTitle = "As It Was",
                trackArtist = "Harry Styles",
                trackThumbnail = R.drawable.img_track_sample_four
            )
        )
        val archiveSaveTrackAdapter = ArchiveSaveTrackAdapter(requireContext(), testArchiveSaveTrackList)
        recyclerviewArchiveSaveTrack.adapter = archiveSaveTrackAdapter
        recyclerviewArchiveSaveTrack.layoutManager = LinearLayoutManager(context)
    }
}