package com.example.remotephonemanager.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remotephonemanager.R
import com.example.remotephonemanager.domain.Device

class DevicesRecyclerViewAdapter(private val devices: MutableList<Device>) :
    RecyclerView.Adapter<DevicesRecyclerViewAdapter.CustomViewHolder>() {

    fun setData(data: MutableList<Device>) {
        devices.clear()
        devices.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_devices_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = devices.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(devices[position])
    }

    inner class CustomViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(device: Device) {
            view.findViewById<TextView>(R.id.decive_name_textView).text = device.name
            view.findViewById<TextView>(R.id.device_model_textView).text = device.model
            /*view.findViewById<Button>(R.id.button).setOnClickListener {
                doTask(device)
            }*/
        }
    }
}