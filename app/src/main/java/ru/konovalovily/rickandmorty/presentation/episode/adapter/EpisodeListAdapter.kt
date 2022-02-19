package ru.konovalovily.rickandmorty.presentation.episode.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.konovalovily.rickandmorty.databinding.EpisodeItemBinding
import ru.konovalovily.rickandmorty.domain.entity.Episode

class EpisodeListAdapter :
    PagingDataAdapter<Episode, EpisodeListAdapter.ViewHolder>(EpisodeItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeListAdapter.ViewHolder {
        return ViewHolder(
            EpisodeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodeListAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class ViewHolder(binding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val tvDateEpisode: TextView = binding.tvDateEpisode
        private val tvNumberEpisode: TextView = binding.tvNumberEpisode
        private val tvTitleEpisode: TextView = binding.tvTitleEpisode

        fun bind(item: Episode) {
            tvDateEpisode.text = item.air_date
            tvNumberEpisode.text = item.episode
            tvTitleEpisode.text = item.name
        }

    }
}