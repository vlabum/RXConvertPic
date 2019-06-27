package ru.vlabum.android.gb.rxconvertpic.model

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class Picture(val contentResolver: ContentResolver, val uri: Uri) : IPicture {

    lateinit var bitmap: Bitmap

    init {
        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
    }

    override fun get(): IPicture {
        return this
    }

    override fun process(): IPicture {
        Thread.sleep(3000)
        saveImage()
        return this
    }

    private fun saveImage() {
        val pth = Environment.getExternalStorageDirectory().toString()
        val file = File(pth, "${UUID.randomUUID()}.png")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            Timber.e(e)
        }
    }
}