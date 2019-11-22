package com.example.remotephonemanager.usecases.devices

import com.example.remotephonemanager.domain.Device

interface DevicesRepository{
    fun getDevices() : List<Device>
}
