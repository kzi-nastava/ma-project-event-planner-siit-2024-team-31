package com.example.ep2024.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ep2024.databinding.ItemServiceBinding
import com.example.ep2024.models.Service

class ServicesAdapter(private var services: List<Service>) :
    RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>() {

    private var onItemClickListener: ((Service) -> Unit)? = null

    fun setOnItemClickListener(listener: (Service) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(newList: List<Service>) {
        services = newList
        notifyDataSetChanged()
    }

    inner class ServiceViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Service) {
            binding.textViewServiceName.text = service.name
            binding.textViewServiceDescription.text = service.description

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(service)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount(): Int = services.size
}