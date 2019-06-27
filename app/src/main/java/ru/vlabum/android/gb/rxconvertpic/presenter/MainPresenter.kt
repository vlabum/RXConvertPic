package ru.vlabum.android.gb.rxconvertpic.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import ru.vlabum.android.gb.rxconvertpic.model.IPicConverter
import ru.vlabum.android.gb.rxconvertpic.view.MainView

@InjectViewState
class MainPresenter() : MvpPresenter<MainView>() {

    private lateinit var mainThreadScheduler: Scheduler
    private var converter: IPicConverter? = null

    constructor(scheduler: Scheduler) : this() {
        this.mainThreadScheduler = scheduler
    }

    fun setConverter(converter: IPicConverter) {
        this.converter = converter
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun convertPicture(): Disposable {
        val d: Disposable =
            mainThreadScheduler.scheduleDirect {
                if (converter == null) return@scheduleDirect
                viewState.showLoading()
                converter!!.convert()
                    .observeOn(mainThreadScheduler)
                    .subscribe { _ ->
                        viewState.hideLoading()
                    }
            }
        return d
    }
}