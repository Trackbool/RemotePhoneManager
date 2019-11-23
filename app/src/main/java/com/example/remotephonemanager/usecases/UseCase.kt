package com.example.remotephonemanager.usecases

abstract class UseCase<I : UseCase.InputData, O : UseCase.OutputData> {
    abstract fun execute(inputData: I, requestCallback: RequestCallback<O>)
    interface InputData
    interface OutputData
    interface RequestCallback<O : OutputData> {
        fun onSuccess(outputData: O)
        fun onError(error: RequestError)
    }
}