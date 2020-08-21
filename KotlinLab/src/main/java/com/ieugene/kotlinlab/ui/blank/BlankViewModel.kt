package com.ieugene.kotlinlab.ui.blank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class BlankViewModel : ViewModel() {
    val word = MutableLiveData<String>()
    val score = MutableLiveData<Int>()

    init {
        word.value = ""
        score.value = 0
        Timber.i("BlankViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("BlankViewModel destroyed!")
    }

    fun onSkip() {
        score.value = (score.value)?.minus(1)
    }

    fun onCorrect() {
        score.value = score.value?.plus(1)
    }
}