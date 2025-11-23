package com.example.flo.mission.presentation.archive

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.mission.model.MusicState
import com.example.flo.R
import com.example.flo.databinding.ItemArchiveSaveAlbumBinding
import com.example.flo.mission.database.entity.AlbumEntity

class ArchiveSaveAlbumAdapter(
    private val items: List<AlbumEntity>,
    private val context: Context
): RecyclerView.Adapter<ArchiveSaveAlbumAdapter.ArchiveSaveAlbumViewHolder>() {

    private var mutableItems: MutableList<AlbumEntity> = items.toMutableList()

    inner class ArchiveSaveAlbumViewHolder(private val binding: ItemArchiveSaveAlbumBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlbumEntity) = with(binding) {
            ivArchiveSaveAlbumThumbnail.setImageDrawable(ContextCompat.getDrawable(context, item.image))
            tvArchiveSaveAlbumTitle.text = item.title
            tvArchiveSaveAlbumArtistName.text = item.artist
            tvArchiveSaveAlbumReleaseDate.text = item.releaseDate
            tvArchiveSaveAlbumFormat.text = item.format
            tvArchiveSaveAlbumGenre.text = item.genre
            ivArchiveSaveAlbumPlay.setImageDrawable(
                when(item.playState) {
                    MusicState.PLAYING -> ContextCompat.getDrawable(context, R.drawable.ic_pause)
                    MusicState.PAUSE -> ContextCompat.getDrawable(context, R.drawable.ic_play_filled)
                    MusicState.RELEASE -> ContextCompat.getDrawable(context, R.drawable.ic_play_filled)
                }
            )
            ivArchiveSaveAlbumPlay.setOnClickListener {
                when(item.playState) {
                    MusicState.PAUSE -> {
                        ivArchiveSaveAlbumPlay.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pause))
                        val newItem = item.copy(playState = MusicState.PLAYING)
                        mutableItems[bindingAdapterPosition] = newItem
                        notifyItemChanged(bindingAdapterPosition)
                    }
                    MusicState.PLAYING -> {
                        ivArchiveSaveAlbumPlay.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play_filled))
                        val newItem = item.copy(playState = MusicState.PAUSE)
                        mutableItems[bindingAdapterPosition] = newItem
                        notifyItemChanged(bindingAdapterPosition)
                    }
                    MusicState.RELEASE -> {
                        ivArchiveSaveAlbumPlay.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pause))
                        val newItem = item.copy(playState = MusicState.PLAYING)
                        mutableItems[bindingAdapterPosition] = newItem
                        notifyItemChanged(bindingAdapterPosition)
                    }
                }
            }
            ivArchiveSaveAlbumOption.setOnClickListener {
                if(bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    mutableItems.removeAt(bindingAdapterPosition)
                    notifyItemRemoved(bindingAdapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArchiveSaveAlbumViewHolder {
        return ArchiveSaveAlbumViewHolder(ItemArchiveSaveAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: ArchiveSaveAlbumViewHolder,
        position: Int
    ) {
        holder.bind(mutableItems[position])
    }

    override fun getItemCount(): Int {
        return mutableItems.size
    }
}