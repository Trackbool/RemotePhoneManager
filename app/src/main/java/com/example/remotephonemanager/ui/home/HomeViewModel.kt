package com.example.remotephonemanager.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remotephonemanager.domain.*
import com.example.remotephonemanager.framework.livedata.LiveEvent
import com.example.remotephonemanager.usecases.RequestError
import com.example.remotephonemanager.usecases.UseCase
import com.example.remotephonemanager.usecases.actions.ActionsRepository
import com.example.remotephonemanager.usecases.actions.ActionsRepositoryMockImpl
import com.example.remotephonemanager.usecases.actions.SendActionUseCase
import com.example.remotephonemanager.usecases.devices.DevicesRepositoryMockImpl
import com.example.remotephonemanager.usecases.devices.GetDevicesUseCase
import java.util.*

class HomeViewModel : ViewModel() {
    private val getDevicesUseCase:
            UseCase<GetDevicesUseCase.InputData,
                    GetDevicesUseCase.OutputData> = GetDevicesUseCase(DevicesRepositoryMockImpl())
    private val sendActionUseCase: UseCase<SendActionUseCase.InputData,
            SendActionUseCase.OutputData> = SendActionUseCase(ActionsRepositoryMockImpl())
    val fetchDevicesError = LiveEvent<String>()
    val sendActionSucceeded = LiveEvent<String>()
    val sendActionError = LiveEvent<String>()
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
        val action = ActionFactory.createTakePhotoAction(device)
        sendAction(action)
    }

    fun onPerformRingClicked(device: Device) {
        println(device.name + " ring")
        val action = ActionFactory.createPerformRingAction(device)
        sendAction(action)
    }

    fun onLockDeviceClicked(device: Device) {
        println(device.name + " lock")
        val action = ActionFactory.createLockDeviceAction(device)
        sendAction(action)
    }

    private fun fetchDevices() {
        loading.set(true)

        getDevicesUseCase.execute(GetDevicesUseCase.InputData(),
            object : UseCase.RequestCallback<GetDevicesUseCase.OutputData> {
                override fun onSuccess(outputData: GetDevicesUseCase.OutputData) {
                    devices.postValue(outputData.devices)
                    loading.set(false)
                }

                override fun onError(error: RequestError) {
                    fetchDevicesError.postValue("Error fetching devices")
                    loading.set(false)
                }
            })
    }

    private fun sendAction(action: Action) {
        loading.set(true)

        sendActionUseCase.execute(SendActionUseCase.InputData(action),
            object : UseCase.RequestCallback<SendActionUseCase.OutputData> {
                override fun onSuccess(outputData: SendActionUseCase.OutputData) {
                    sendActionSucceeded.postValue("Action sent to: " + action.dstDevice.name)
                    loading.set(false)
                }

                override fun onError(error: RequestError) {
                    sendActionError.postValue("Error sending action to: " + action.dstDevice.name)
                    loading.set(false)
                }
            })
    }
}