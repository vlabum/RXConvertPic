package ru.vlabum.android.gb.rxconvertpic.model

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PicConverter(picture: IPicture) : IPicConverter {

    val pic: IPicture = picture

    override fun convert(): Single<IPicture> {
        return Single
            .fromCallable { pic.process() }
            .subscribeOn(Schedulers.computation())
    }
}