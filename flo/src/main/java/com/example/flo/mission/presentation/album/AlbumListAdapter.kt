package com.example.flo.mission.presentation.album

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.mission.model.TrackModel
import com.example.flo.databinding.ItemAlbumListBinding
import java.util.Locale

class AlbumListAdapter(private val albumList: MutableList<TrackModel>): RecyclerView.Adapter<AlbumListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAlbumListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrackModel) = with(binding) {
            tvAlbumListTrackNumber.text = String.format(Locale.getDefault(), "%02d", item.trackId)
            tvAlbumListTrackTitle.text = item.trackTitle
            tvAlbumListTrackArtist.text = item.trackArtist
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemAlbumListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(albumList[position])
    }

    override fun getItemCount() = albumList.size
}