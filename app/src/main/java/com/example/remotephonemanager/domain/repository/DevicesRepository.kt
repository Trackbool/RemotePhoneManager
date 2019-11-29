package com.example.remotephonemanager.domain.repository

import com.example.remotephonemanager.domain.entities.Device
import com.example.remotephonemanager.domain.entities.request.RequestError

interface DevicesRepository{
    fun getDevices(getDevicesCallback: GetDevicesCallback)

    interface GetDevicesCallback{
        fun onSuccess(devices: List<Device>)
        fun onError(requestError: RequestError)
    }
}
