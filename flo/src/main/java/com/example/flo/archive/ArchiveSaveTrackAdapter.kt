package com.example.flo.archive

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.TrackModel
import com.example.flo.R
import com.example.flo.databinding.ItemArchiveSaveTrackBinding

class ArchiveSaveTrackAdapter(private val context: Context, private val trackList: MutableList<TrackModel>): RecyclerView.Adapter<ArchiveSaveTrackAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemArchiveSaveTrackBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrackModel) = with(binding) {
            ivArchiveSaveTrackThumbnail.setImageDrawable(ContextCompat.getDrawable(context, item.trackThumbnail ?: R.drawable.img_no_track))
            tvArchiveSaveTrackTitle.text = item.trackTitle
            tvArchiveSaveTrackArtist.text = item.trackArtist
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemArchiveSaveTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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