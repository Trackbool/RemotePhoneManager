package com.example.remotephonemanager.domain.repository

import com.example.remotephonemanager.domain.entities.Action
import com.example.remotephonemanager.domain.entities.request.RequestError

interface ActionsRepository {
    fun sendAction(action: Action, sendActionCallback: SendActionCallback)

    interface SendActionCallback {
        fun onSuccess()
        fun onError(requestError: RequestError)
    }

    fun getActions(getActionsCallback: GetActionsCallback)

    interface GetActionsCallback{
        fun onSuccess(actions: List<Action>)
        fun onError(requestError: RequestError)
    }
}
