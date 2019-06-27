package ru.vlabum.android.gb.rxconvertpic.model

import android.graphics.Bitmap
import android.os.Environment
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class PicConverter(picture: IPicture) : IPicConverter {

    val pic: IPicture = picture

    override fun convert(): Single<Unit> {
        return Single
            .fromCallable { convert(pic) }
            .subscribeOn(Schedulers.computation())
    }

    private fun convert(pic: IPicture) {
        Thread.sleep(3000)
        saveImage(pic.getBitmap())
    }

    private fun saveImage(bitmap: Bitmap) {
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