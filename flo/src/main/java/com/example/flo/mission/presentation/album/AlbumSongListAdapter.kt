package com.example.flo.mission.presentation.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSongListBinding
import com.example.flo.mission.database.entity.SongEntity
import java.util.Locale

class AlbumSongListAdapter(private val songs: List<SongEntity>): RecyclerView.Adapter<AlbumSongListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSongListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SongEntity) = with(binding) {
            tvAlbumListTrackNumber.text = String.format(Locale.getDefault(), "%02d", bindingAdapterPosition+1)
            tvAlbumListTrackTitle.text = item.title
            tvAlbumListTrackArtist.text = item.artist
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(songs[position])
    }

    override fun getItemCount() = songs.size
}