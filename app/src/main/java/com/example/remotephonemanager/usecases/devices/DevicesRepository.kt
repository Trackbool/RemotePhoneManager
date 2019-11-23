package com.example.remotephonemanager.usecases.devices

import com.example.remotephonemanager.domain.Device
import com.example.remotephonemanager.usecases.RequestError

interface DevicesRepository{
    fun getDevices(getDevicesCallback: GetDevicesCallback)

    interface GetDevicesCallback{
        fun onSuccess(devices: List<Device>)
        fun onError(requestError: RequestError)
    }
}
