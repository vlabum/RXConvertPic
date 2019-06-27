package ru.vlabum.android.gb.rxconvertpic.model

import io.reactivex.Single

interface IPicConverter {
    fun convert(): Single<Unit>
}