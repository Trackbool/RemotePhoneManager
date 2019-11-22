package com.example.remotephonemanager.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remotephonemanager.domain.Device
import com.example.remotephonemanager.domain.User
import com.example.remotephonemanager.usecases.UseCase
import com.example.remotephonemanager.usecases.devices.DevicesRepositoryMockImpl
import com.example.remotephonemanager.usecases.devices.GetDevicesUseCase
import kotlin.properties.ObservableProperty

class HomeViewModel : ViewModel() {
    private val getDevicesUseCase:
            UseCase<GetDevicesUseCase.InputData,
                    GetDevicesUseCase.OutputData> = GetDevicesUseCase(DevicesRepositoryMockImpl())

    var loading: ObservableField<Boolean> = ObservableField()
    val devices = ObservableField<List<Device>>()

    init {
        loading.set(false)
        fetchDevices()
    }

    fun onUpdateDevicesListRequest(){
        fetchDevices()
    }

    private fun fetchDevices(){
        loading.set(true)

        val authenticatedUser = User(1, "usuario1", "",
            Device("001A", "Movil Adri", "Xiaomi"))
        val fetchedDevices = getDevicesUseCase
            .execute(GetDevicesUseCase.InputData(authenticatedUser)).devices

        devices.set(fetchedDevices)
        loading.set(false)
    }
}