package com.example.flo.mission.presentation.archive

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.mission.model.TrackModel
import com.example.flo.R
import com.example.flo.databinding.ItemArchiveSaveTrackBinding

class ArchiveSaveTrackAdapter(private val context: Context, trackList: MutableList<TrackModel>): RecyclerView.Adapter<ArchiveSaveTrackAdapter.ViewHolder>() {

    private var trackLists: MutableList<TrackModel>

    init {
        this.trackLists = trackList
    }

    inner class ViewHolder(private val binding: ItemArchiveSaveTrackBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrackModel) = with(binding) {
            ivArchiveSaveTrackThumbnail.setImageDrawable(ContextCompat.getDrawable(context, item.trackThumbnail ?: R.drawable.img_no_track))
            tvArchiveSaveTrackTitle.text = item.trackTitle
            tvArchiveSaveTrackArtist.text = item.trackArtist
            ivArchiveSaveTrackOption.setOnClickListener {
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    trackLists.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
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
        holder.bind(trackLists[position])
    }

    override fun getItemCount(): Int {
        return trackLists.size
    }

}