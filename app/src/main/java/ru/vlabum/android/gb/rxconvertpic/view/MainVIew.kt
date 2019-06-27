package ru.vlabum.android.gb.rxconvertpic.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(text: String)

    fun showLoading()
    fun hideLoading()
}
