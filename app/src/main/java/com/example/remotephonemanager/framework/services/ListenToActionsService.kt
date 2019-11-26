package com.example.remotephonemanager.framework.services

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.media.Image
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.TextureView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.remotephonemanager.R
import com.example.remotephonemanager.framework.camera.EZCam
import com.example.remotephonemanager.framework.camera.EZCamCallback
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


class ListenToActionsService : Service() {
    private val NOTIFICATION_ID = 1000
    private var mNotificationBuilder: Notification.Builder? = null
    private var mNotificationManager: NotificationManager? = null

    /*private val getActionsUseCase = GetActionsUseCase(ActionsRepositoryMockImpl())
    private val getActionsRequestCallback =
        object : UseCase.RequestCallback<GetActionsUseCase.OutputData> {
            override fun onSuccess(outputData: GetActionsUseCase.OutputData) {
                manageActions(outputData.actions)
            }

            override fun onError(error: RequestError) {}
        } */

    private lateinit var camera: EZCam
    private val cameraCallback = object : EZCamCallback {
        override fun onCameraReady() {
            camera.startPreview()
            camera.takePicture()
        }

        override fun onPicture(image: Image?) {
            Log.d("CameraServiceDebug", "Picture taken")
            val buffer: ByteBuffer = image!!.planes[0].buffer
            val bytes = ByteArray(buffer.capacity())
            buffer.get(bytes)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)

            //compress
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray = stream.toByteArray()
            val compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            //val imageBase64 = Base64.encodeToString(byteArray, Base64.NO_WRAP)
        }

        override fun onCameraDisconnected() {
            Log.d("CameraServiceDebug", "Camera disconnected")
        }

        override fun onError(message: String?) {
            Log.d("CameraServiceDebug", "Error: $message")
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Handler().post {
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
            this?.setContentTitle("App is running")
                ?.setTicker("Running")
                ?.setSmallIcon(R.drawable.ic_launcher_background)
                ?.setContentText("Listening...")
                ?.setOngoing(true)

            mNotificationManager?.notify(NOTIFICATION_ID, mNotificationBuilder?.build())
        }
    }

    private fun doTask() {
        camera = EZCam(applicationContext)
        takePhoto()
        /*while (true) {
            receiveActions()
        }*/
    }

    /*private fun receiveActions() {
        //TODO: WebSocket to get actions in real time
        Thread.sleep(3000)
        getActionsUseCase.execute(GetActionsUseCase.InputData(), getActionsRequestCallback)
    }

    private fun manageActions(actions: List<Action>){
        //TODO: execute actions depending on each action type
        takePhoto()
    }*/

    private fun takePhoto() {
        if(hasCameraPermission()) {
            val id = camera.camerasList[CameraCharacteristics.LENS_FACING_BACK]
            camera.selectCamera(id)
            camera.setCameraCallback(cameraCallback)
            val textureView = TextureView(applicationContext)
            textureView.surfaceTexture = SurfaceTexture(1)
            camera.open(CameraDevice.TEMPLATE_PREVIEW, textureView)
        }
    }

    private fun hasCameraPermission(): Boolean{
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
}