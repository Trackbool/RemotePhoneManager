package com.example.remotephonemanager.ui.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.remotephonemanager.domain.Device

@BindingAdapter("data")
fun setRecyclerViewProperties(recyclerView: RecyclerView?, data: MutableList<Device>?) {
    val adapter = recyclerView?.adapter
    if (adapter is DevicesRecyclerViewAdapter && data != null) {
        adapter.setData(data)
    }
}