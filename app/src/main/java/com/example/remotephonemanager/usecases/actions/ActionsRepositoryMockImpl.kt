package com.example.remotephonemanager.usecases.actions

import com.example.remotephonemanager.domain.Action

class ActionsRepositoryMockImpl : ActionsRepository {
    override fun sendAction(action: Action, sendActionCallback: ActionsRepository.SendActionCallback) {
        sendActionCallback.onSuccess()
    }
}