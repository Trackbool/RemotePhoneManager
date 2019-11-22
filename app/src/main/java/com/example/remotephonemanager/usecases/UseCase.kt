package com.example.remotephonemanager.usecases

abstract class UseCase<I : UseCase.InputData, O : UseCase.OutputData> {
    abstract fun execute(inputData: I): O
    interface InputData
    interface OutputData
}