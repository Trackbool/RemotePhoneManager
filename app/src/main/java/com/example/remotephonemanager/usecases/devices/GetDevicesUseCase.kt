package com.example.remotephonemanager.usecases.devices

import com.example.remotephonemanager.domain.entities.Device
import com.example.remotephonemanager.domain.repository.DevicesRepository
import com.example.remotephonemanager.domain.entities.request.RequestError
import com.example.remotephonemanager.usecases.UseCase

class GetDevicesUseCase(private val devicesRepository: DevicesRepository) :
    UseCase<GetDevicesUseCase.InputData, GetDevicesUseCase.OutputData>() {

    override fun execute(inputData: InputData, requestCallback: RequestCallback<OutputData>) {
        val repositoryRequestCallback = object : DevicesRepository.GetDevicesCallback{
            override fun onSuccess(devices: List<Device>) {
                requestCallback.onSuccess(OutputData(devices))
            }

            override fun onError(requestError: RequestError) {
                requestCallback.onError(requestError)
            }

        }
        devicesRepository.getDevices(repositoryRequestCallback)
    }

    class InputData : UseCase.InputData
    class OutputData(val devices: List<Device>): UseCase.OutputData
}