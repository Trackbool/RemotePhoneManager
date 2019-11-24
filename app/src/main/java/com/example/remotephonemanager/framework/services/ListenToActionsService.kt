package com.example.remotephonemanager.framework.services

import android.app.IntentService
import android.content.Intent
import com.example.remotephonemanager.domain.Action
import com.example.remotephonemanager.usecases.RequestError
import com.example.remotephonemanager.usecases.UseCase
import com.example.remotephonemanager.usecases.actions.ActionsRepositoryMockImpl
import com.example.remotephonemanager.usecases.actions.GetActionsUseCase

class ListenToActionsService : IntentService("ListenToActionsService") {
    private val getActionsUseCase = GetActionsUseCase(ActionsRepositoryMockImpl())
    private val getActionsRequestCallback =
        object : UseCase.RequestCallback<GetActionsUseCase.OutputData> {
            override fun onSuccess(outputData: GetActionsUseCase.OutputData) {
                manageActions(outputData.actions)
            }

            override fun onError(error: RequestError) {}
        }

    override fun onHandleIntent(intent: Intent?) {
        while (true) {
            receiveActions()
        }
    }

    private fun receiveActions() {
        Thread.sleep(3000)
        getActionsUseCase.execute(GetActionsUseCase.InputData(), getActionsRequestCallback)
    }

    private fun manageActions(actions: List<Action>) {
        //println(actions[0].typeId)
    }
}