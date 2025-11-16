package com.example.flo.mission.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.mission.model.TrackModel
import com.example.flo.databinding.ItemHomeTodayReleasedAlbumBinding
import com.example.flo.R

class HomeTrackAdapter(private val context: Context, private val trackList: MutableList<TrackModel>, private val onClicked: (TrackModel) -> Unit): RecyclerView.Adapter<HomeTrackAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHomeTodayReleasedAlbumBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrackModel) = with(binding) {
            ivTodayReleasedTrack.setImageDrawable(ContextCompat.getDrawable(context, item.trackThumbnail ?: R.drawable.img_no_track))
            tvTodayReleasedTrackTitle.text = item.trackTitle
            tvTodayReleasedTrackArtist.text = item.trackArtist
            root.setOnClickListener { onClicked(item) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemHomeTodayReleasedAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int {
        return trackList.size
    }
}