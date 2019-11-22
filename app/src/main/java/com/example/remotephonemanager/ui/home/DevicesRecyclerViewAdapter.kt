package com.example.remotephonemanager.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remotephonemanager.R
import com.example.remotephonemanager.domain.Device

class DevicesRecyclerViewAdapter(private val devices: MutableList<Device>,
                                 private val events: Events) :
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
            view.findViewById<ImageView>(R.id.take_photo_button).setOnClickListener {
                events.onTakePhotoClicked(device)
            }
            view.findViewById<ImageView>(R.id.trigger_ring_button).setOnClickListener {
                events.onPerformRingClicked(device)
            }
            view.findViewById<ImageView>(R.id.lock_device_button).setOnClickListener {
                events.onLockDeviceClicked(device)
            }
        }
    }

    interface Events {
        fun onTakePhotoClicked(device: Device)
        fun onPerformRingClicked(device: Device)
        fun onLockDeviceClicked(device: Device)
    }
}