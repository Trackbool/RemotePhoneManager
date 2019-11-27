package com.example.remotephonemanager.framework.services.listen_actions

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.view.TextureView
import androidx.core.content.ContextCompat
import com.example.remotephonemanager.R
import com.example.remotephonemanager.domain.Action
import com.example.remotephonemanager.domain.ActionType
import com.example.remotephonemanager.framework.camera.EZCam
import com.example.remotephonemanager.usecases.RequestError
import com.example.remotephonemanager.usecases.UseCase
import com.example.remotephonemanager.usecases.actions.ActionsRepositoryMockImpl
import com.example.remotephonemanager.usecases.actions.GetActionsUseCase


class ListenToActionsService : Service() {
    private val notificationId = 1000
    private lateinit var mNotificationBuilder: Notification.Builder
    private lateinit var mNotificationManager: NotificationManager

    private val getActionsUseCase = GetActionsUseCase(ActionsRepositoryMockImpl())
    private val getActionsRequestCallback =
        object : UseCase.RequestCallback<GetActionsUseCase.OutputData> {
            override fun onSuccess(outputData: GetActionsUseCase.OutputData) {
                manageActions(outputData.actions)
            }

            override fun onError(error: RequestError) {}
        }

    private val handler = Handler()
    private lateinit var camera: EZCam

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        handler.post {
            initializeNotificationBuilder()
            doTask()
        }

        return START_NOT_STICKY
    }

    private fun initializeNotificationBuilder() {
        mNotificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, "1")
        } else {
            Notification.Builder(this)
        }
        with(mNotificationBuilder) {
            this.setContentTitle("App is running")
                .setTicker("Running")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("Listening...")
                .setOngoing(true)

            mNotificationManager.notify(notificationId, mNotificationBuilder.build())
        }
    }

    override fun onDestroy() {
        mNotificationManager.cancel(notificationId)
        super.onDestroy()
    }

    private fun doTask() {
        camera = EZCam(applicationContext)
        camera.setCameraCallback(
            TakePhotoCameraCallback(
                camera
            )
        )

        //TODO: WebSocket to get actions in real time instead of polling
        lateinit var runnableCode: Runnable
        runnableCode = Runnable {
            receiveActions()
            handler.postDelayed(runnableCode, 3400)
        }
        handler.post(runnableCode)
    }

    private fun receiveActions() {
        getActionsUseCase.execute(GetActionsUseCase.InputData(), getActionsRequestCallback)
    }

    private fun manageActions(actions: List<Action>) {
        for (action in actions) {
            when (action.typeId) {
                ActionType.TAKE_PHOTO -> takePhoto(CameraCharacteristics.LENS_FACING_BACK)
                //ActionType.LOCK_DEVICE -> //TODO: lockDevice()
                //ActionType.PERFORM_RING -> //TODO: performRing()
            }
            //TODO: set actions resolved
        }

    }

    private fun takePhoto(cameraType: Int) {
        if (hasCameraPermission()) {
            val id = camera.camerasList[cameraType]
            camera.selectCamera(id)
            val textureView = TextureView(applicationContext)
            textureView.surfaceTexture = SurfaceTexture(1)
            camera.open(CameraDevice.TEMPLATE_PREVIEW, textureView)
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
    }
}