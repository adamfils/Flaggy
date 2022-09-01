package com.techguy.flaggy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.techguy.flaggy.R
import com.techguy.flaggy.databinding.CountryItemBinding
import com.techguy.flaggy.model.CountryModel

class CountryAdapter : Adapter<CountryVH>() {

    private var data = ArrayList<CountryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return CountryVH(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CountryVH, position: Int) {
        holder.bind(data[position])
    }

    fun setList(data: ArrayList<CountryModel>) {
        this.data = data
        notifyDataSetChanged()
    }
}

class CountryVH(itemView: View) : ViewHolder(itemView) {
    private val binding = CountryItemBinding.bind(itemView)

    fun bind(countryModel: CountryModel) {
        binding.countryCapital.text = countryModel.capital
        val name = "${countryModel.name}, ${countryModel.region}   ${countryModel.code}"
        binding.countryName.text = name
    }
}