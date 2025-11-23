package com.example.flo.mission.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemHomeTodayReleasedAlbumBinding
import com.example.flo.R
import com.example.flo.mission.database.entity.AlbumEntity

class HomeAlbumAdapter(private val context: Context, private val trackList: List<AlbumEntity>, private val onClicked: (AlbumEntity) -> Unit): RecyclerView.Adapter<HomeAlbumAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHomeTodayReleasedAlbumBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlbumEntity) = with(binding) {
            ivTodayReleasedTrack.setImageDrawable(ContextCompat.getDrawable(context, item.image ?: R.drawable.img_no_track))
            tvTodayReleasedTrackTitle.text = item.title
            tvTodayReleasedTrackArtist.text = item.artist
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