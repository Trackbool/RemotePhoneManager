package com.example.remotephonemanager.usecases.actions

import com.example.remotephonemanager.domain.Action
import com.example.remotephonemanager.usecases.RequestError

interface ActionsRepository {
    fun sendAction(action: Action, sendActionCallback: SendActionCallback)

    interface SendActionCallback {
        fun onSuccess()
        fun onError(requestError: RequestError)
    }
}
