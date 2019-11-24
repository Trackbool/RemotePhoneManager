package com.example.remotephonemanager.usecases

data class RequestError(val type: RequestErrorType, val errorMessage: String = "")