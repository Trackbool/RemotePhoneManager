package com.example.remotephonemanager.framework.services.listen_actions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import com.example.remotephonemanager.framework.camera.EZCam
import com.example.remotephonemanager.framework.camera.EZCamCallback
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class TakePhotoCameraCallback(private val camera: EZCam) : EZCamCallback {
    override fun onCameraReady() {
        camera.startPreview()
        camera.takePicture()
    }

    override fun onPicture(image: Image?) {
        Log.d("CameraServiceDebug", "Picture taken")
        camera.close()
        processImage(image)
    }

    override fun onCameraDisconnected() {
        Log.d("CameraServiceDebug", "Camera disconnected")
    }

    override fun onError(message: String?) {
        Log.d("CameraServiceDebug", "Error: $message")
    }

    private fun processImage(image: Image?) {
        val buffer: ByteBuffer = image!!.planes[0].buffer
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
        val byteArray = stream.toByteArray()
        val compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        //val imageBase64 = Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }
}
