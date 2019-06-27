package ru.vlabum.android.gb.rxconvertpic.model

interface IPicture {
    fun get(): IPicture
    fun process(): IPicture
}