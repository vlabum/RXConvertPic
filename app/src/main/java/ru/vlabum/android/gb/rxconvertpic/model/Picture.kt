package ru.vlabum.android.gb.rxconvertpic.model

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore

class Picture(val contentResolver: ContentResolver, val uri: Uri) : IPicture {

    var bmap: Bitmap

    init {
        bmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
    }

    override fun getBitmap(): Bitmap {
        return bmap
    }

//    override fun process(): IPicture {
//        Thread.sleep(3000)
//        saveImage()
//        return this
//    }
//
//    private fun saveImage() {
//        val pth = Environment.getExternalStorageDirectory().toString()
//        val file = File(pth, "${UUID.randomUUID()}.png")
//        try {
//            val stream: OutputStream = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            stream.flush()
//            stream.close()
//        } catch (e: IOException) {
//            Timber.e(e)
//        }
//    }
}