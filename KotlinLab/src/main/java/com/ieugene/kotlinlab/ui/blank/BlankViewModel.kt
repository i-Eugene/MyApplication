package com.ieugene.kotlinlab.ui.blank

import android.app.Application
import androidx.lifecycle.*
import com.ieugene.kotlinlab.database.SleepDataBaseDao
import com.ieugene.kotlinlab.database.SleepNight
import kotlinx.coroutines.launch
import timber.log.Timber

class BlankViewModel(application: Application, val dataBaseDao: SleepDataBaseDao) : AndroidViewModel(application) {
    val word = MutableLiveData<String>()
    val score = MutableLiveData<Int>()
    val scoreValue: Int = 0
    val tonight = MutableLiveData<SleepNight>()

    val _score: LiveData<Int>
        get() = score

    init {
        word.value = ""
        score.value = 0
        initTonight()
        Timber.i("BlankViewModel created!")
    }

    private fun initTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
            Timber.d("tonightID=${tonight.value?.nightId}")
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        var tonight = dataBaseDao.getTonight()
//        if (tonight?.endTimeMilli != tonight?.startTimeMilli) {
//            tonight = null
//        }
        Timber.d("getTonightFromDatabase")
        return tonight
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("BlankViewModel destroyed!")
    }

    fun onSkip() {
        score.value = (score.value)?.minus(1)
//        val t = Transformations.map(score) {it ->
//            DateUtils.formatElapsedTime(it.toLong())
//        }

    }

    fun onCorrect() {
        score.value = score.value?.plus(1)
    }
}