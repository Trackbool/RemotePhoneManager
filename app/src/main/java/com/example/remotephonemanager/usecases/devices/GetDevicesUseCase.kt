package com.example.remotephonemanager.usecases.devices

import com.example.remotephonemanager.domain.Device
import com.example.remotephonemanager.domain.User
import com.example.remotephonemanager.usecases.UseCase

class GetDevicesUseCase(private val devicesRepository: DevicesRepository) :
    UseCase<GetDevicesUseCase.InputData, GetDevicesUseCase.OutputData>() {

    override fun execute(inputData: InputData): OutputData {
        return OutputData(devicesRepository.getDevices())
    }

    class InputData(var user: User) : UseCase.InputData
    class OutputData(val devices: List<Device>): UseCase.OutputData
}