package com.example.flo.mission.presentation.archive

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.mission.model.MusicState
import com.example.flo.R
import com.example.flo.databinding.ItemArchiveSaveAlbumBinding
import com.example.flo.mission.model.AlbumModel

class ArchiveSaveAlbumAdapter(
    private val items: List<AlbumModel>,
    private val context: Context
): RecyclerView.Adapter<ArchiveSaveAlbumAdapter.ArchiveSaveAlbumViewHolder>() {

    private var mutableItems: MutableList<AlbumModel> = items.toMutableList()

    inner class ArchiveSaveAlbumViewHolder(private val binding: ItemArchiveSaveAlbumBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlbumModel) = with(binding) {
            ivArchiveSaveAlbumThumbnail.setImageDrawable(ContextCompat.getDrawable(context, item.albumThumbnail))
            tvArchiveSaveAlbumTitle.text = item.albumTitle
            tvArchiveSaveAlbumArtistName.text = item.albumArtistName
            tvArchiveSaveAlbumReleaseDate.text = item.albumReleaseDate
            tvArchiveSaveAlbumFormat.text = item.albumFormat
            tvArchiveSaveAlbumGenre.text = item.albumGenre
            ivArchiveSaveAlbumPlay.setImageDrawable(
                when(item.albumState) {
                    MusicState.PLAYING -> ContextCompat.getDrawable(context, R.drawable.ic_pause)
                    MusicState.PAUSE -> ContextCompat.getDrawable(context, R.drawable.ic_play_filled)
                    MusicState.RELEASE -> ContextCompat.getDrawable(context, R.drawable.ic_play_filled)
                }
            )
            ivArchiveSaveAlbumPlay.setOnClickListener {
                when(item.albumState) {
                    MusicState.PAUSE -> {
                        ivArchiveSaveAlbumPlay.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pause))
                        val newItem = item.copy(albumState = MusicState.PLAYING)
                        mutableItems[bindingAdapterPosition] = newItem
                        notifyItemChanged(bindingAdapterPosition)
                    }
                    MusicState.PLAYING -> {
                        ivArchiveSaveAlbumPlay.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play_filled))
                        val newItem = item.copy(albumState = MusicState.PAUSE)
                        mutableItems[bindingAdapterPosition] = newItem
                        notifyItemChanged(bindingAdapterPosition)
                    }
                    MusicState.RELEASE -> {
                        ivArchiveSaveAlbumPlay.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pause))
                        val newItem = item.copy(albumState = MusicState.PLAYING)
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