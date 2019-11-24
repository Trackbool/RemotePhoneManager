package com.example.remotephonemanager.usecases.actions

import com.example.remotephonemanager.domain.Action
import com.example.remotephonemanager.usecases.RequestError
import com.example.remotephonemanager.usecases.UseCase

class SendActionUseCase(private val actionsRepository: ActionsRepository) :
    UseCase<SendActionUseCase.InputData, SendActionUseCase.OutputData>() {

    override fun execute(inputData: InputData, requestCallback: RequestCallback<OutputData>) {
        val repositoryRequestCallback = object : ActionsRepository.SendActionCallback{
            override fun onSuccess() {
                requestCallback.onSuccess(OutputData())
            }

            override fun onError(requestError: RequestError) {
                requestCallback.onError(requestError)
            }

        }
        actionsRepository.sendAction(inputData.action, repositoryRequestCallback)
    }

    class InputData(var action: Action) : UseCase.InputData
    class OutputData : UseCase.OutputData
}