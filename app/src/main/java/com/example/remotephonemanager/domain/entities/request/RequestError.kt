package com.example.remotephonemanager.domain.entities.request

data class RequestError(val type: RequestErrorType, val errorMessage: String = "")