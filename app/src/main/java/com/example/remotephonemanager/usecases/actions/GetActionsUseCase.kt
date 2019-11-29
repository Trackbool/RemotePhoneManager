package com.example.remotephonemanager.usecases.actions

import com.example.remotephonemanager.domain.entities.Action
import com.example.remotephonemanager.domain.repository.ActionsRepository
import com.example.remotephonemanager.domain.entities.request.RequestError
import com.example.remotephonemanager.usecases.UseCase

class GetActionsUseCase(private var actionsRepository: ActionsRepository)  :
    UseCase<GetActionsUseCase.InputData, GetActionsUseCase.OutputData>() {

    override fun execute(inputData: InputData, requestCallback: RequestCallback<OutputData>) {
        val repositoryRequestCallback = object : ActionsRepository.GetActionsCallback{
            override fun onSuccess(actions: List<Action>) {
                requestCallback.onSuccess(OutputData(actions))
            }

            override fun onError(requestError: RequestError) {
                requestCallback.onError(requestError)
            }
        }
        actionsRepository.getActions(repositoryRequestCallback)
    }

    class InputData : UseCase.InputData
    class OutputData(var actions: List<Action>) : UseCase.OutputData
}