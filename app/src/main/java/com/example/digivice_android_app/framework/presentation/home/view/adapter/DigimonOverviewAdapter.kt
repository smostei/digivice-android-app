package com.example.digivice_android_app.framework.presentation.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.digivice_android_app.R
import com.example.digivice_android_app.databinding.AdapterDigimonOverviewBinding
import com.example.digivice_android_app.domain.Digimon
import com.example.digivice_android_app.framework.extensions.capitalized
import com.squareup.picasso.Picasso

class DigimonOverviewAdapter(
    private var digimonList: List<Digimon>,
) : RecyclerView.Adapter<DigimonOverviewAdapter.DigimonViewHolder>() {

    private val totalDigimons = digimonList
    var listener: OnDigimonClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigimonViewHolder {
        val binding = AdapterDigimonOverviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DigimonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DigimonViewHolder, position: Int) =
        holder.bindItem(digimonList[position])

    override fun getItemCount(): Int = digimonList.size

    @SuppressWarnings("NotifyDataSetChanged")
    fun filterDigimonByName(name: String?) {
        digimonList = totalDigimons.filter { it.name == name?.capitalized() }
        notifyDataSetChanged()
    }

    @SuppressWarnings("NotifyDataSetChanged")
    fun filterDigimonsByLevel(level: String?) {
        digimonList = totalDigimons.filter { it.level?.levelDescription() == level }
        notifyDataSetChanged()
    }

    @SuppressWarnings("NotifyDataSetChanged")
    fun showAllDigimons() {
        digimonList = totalDigimons
        notifyDataSetChanged()
    }

    inner class DigimonViewHolder(private val binding: AdapterDigimonOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(digimon: Digimon) = with(binding) {
            digimonName.text = digimon.name
            Picasso
                .get()
                .load(digimon.image)
                .placeholder(R.drawable.ic_digimon_placeholder)
                .into(digimonImage)

            digimonContainer.setOnClickListener { listener?.onDigimonPressed(digimon) }
        }
    }

    interface OnDigimonClickListener {
        fun onDigimonPressed(digimonSelected: Digimon)
    }
}