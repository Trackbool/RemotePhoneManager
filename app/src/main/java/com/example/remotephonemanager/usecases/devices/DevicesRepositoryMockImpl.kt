package com.example.remotephonemanager.usecases.devices

import com.example.remotephonemanager.domain.Device

class DevicesRepositoryMockImpl : DevicesRepository {

    override fun getDevices(getDevicesCallback: DevicesRepository.GetDevicesCallback){
        Thread(Runnable {
            Thread.sleep(3000)
            val devices = ArrayList<Device>()
            devices.add(Device("001A", "Movil Adrián", "Xiaomi Mi4"))
            devices.add(Device("002A", "Samsung María", "Samsung A5"))
            devices.add(Device("003A", "Movil Randall", "iPhone X"))
            devices.add(Device("004A", "BQ Andreu", "BQ Spain T"))

            getDevicesCallback.onSuccess(devices)
        }).start()

    }
}