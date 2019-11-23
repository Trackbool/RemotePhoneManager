package com.example.remotephonemanager.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remotephonemanager.domain.Device
import com.example.remotephonemanager.domain.User
import com.example.remotephonemanager.usecases.RequestError
import com.example.remotephonemanager.usecases.UseCase
import com.example.remotephonemanager.usecases.devices.DevicesRepositoryMockImpl
import com.example.remotephonemanager.usecases.devices.GetDevicesUseCase

class HomeViewModel : ViewModel() {
    private val getDevicesUseCase:
            UseCase<GetDevicesUseCase.InputData,
                    GetDevicesUseCase.OutputData> = GetDevicesUseCase(DevicesRepositoryMockImpl())

    var loading = ObservableField<Boolean>()
    var devices: MutableLiveData<List<Device>> = MutableLiveData()

    init {
        fetchDevices()
    }

    fun onUpdateDevicesListRequest() {
        fetchDevices()
    }

    fun onTakePhotoClicked(device: Device) {
        println(device.name + " photo")
    }

    fun onPerformRingClicked(device: Device) {
        println(device.name + " ring")
    }

    fun onLockDeviceClicked(device: Device) {
        println(device.name + " lock")
    }

    private fun fetchDevices() {
        loading.set(true)

        val authenticatedUser = User(
            1, "usuario1", "",
            Device("001A", "Movil Adri", "Xiaomi")
        )
        getDevicesUseCase.execute(GetDevicesUseCase.InputData(authenticatedUser),
            object : UseCase.RequestCallback<GetDevicesUseCase.OutputData> {
                override fun onSuccess(outputData: GetDevicesUseCase.OutputData) {
                    devices.postValue(outputData.devices)
                    loading.set(false)
                }

                override fun onError(error: RequestError) {
                    loading.set(false)
                    TODO()
                }
            })
    }
}