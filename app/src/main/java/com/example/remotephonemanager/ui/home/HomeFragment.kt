package com.example.remotephonemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remotephonemanager.R
import com.example.remotephonemanager.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var devicesListRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val binding: FragmentHomeBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        devicesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DevicesRecyclerViewAdapter(mutableListOf())
            setHasFixedSize(true)
        }
    }

    private fun initViews() {
        devicesListRecyclerView = view!!.findViewById(R.id.devices_list_recyclerView)
    }
}