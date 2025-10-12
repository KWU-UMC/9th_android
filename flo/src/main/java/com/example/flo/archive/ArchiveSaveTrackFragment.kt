package com.example.flo.archive

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.R
import com.example.flo.TrackModel
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
            ),
            TrackModel(
                trackTitle = "Blinding Lights",
                trackArtist = "The Weekend",
                trackThumbnail = R.drawable.img_track_sample_one
            ),
            TrackModel(
                trackTitle = "Good 4 u",
                trackArtist = "Olivia Rodrigo",
                trackThumbnail = R.drawable.img_track_sample_two
            ),
            TrackModel(
                trackTitle = "Levitating",
                trackArtist = "Dua Lipa",
                trackThumbnail = R.drawable.img_track_sample_three
            ),
            TrackModel(
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