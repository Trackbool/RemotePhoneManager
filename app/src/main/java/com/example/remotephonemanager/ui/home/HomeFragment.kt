package com.example.remotephonemanager.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remotephonemanager.R
import com.example.remotephonemanager.databinding.FragmentHomeBinding
import com.example.remotephonemanager.domain.entities.Device
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var devicesListRecyclerView: RecyclerView
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val binding: FragmentHomeBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.fetchDevicesError.observe(this, Observer {
            val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        })
        viewModel.sendActionSucceeded.observe(this, Observer {
            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
        })
        viewModel.sendActionError.observe(this, Observer {
            val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        })
        setUpRecyclerView()
    }

    private fun initViews() {
        devicesListRecyclerView = view!!.findViewById(R.id.devices_list_recyclerView)
    }

    private fun setUpRecyclerView() {
        devicesListRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = DevicesRecyclerViewAdapter(mutableListOf(),
                object : DevicesRecyclerViewAdapter.Events {
                    override fun onTakePhotoClicked(device: Device) {
                        viewModel.onTakePhotoClicked(device)
                    }

                    override fun onPerformRingClicked(device: Device) {
                        viewModel.onPerformRingClicked(device)
                    }

                    override fun onLockDeviceClicked(device: Device) {
                        viewModel.onLockDeviceClicked(device)
                    }
                })
        }
    }
}