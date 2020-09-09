package com.ieugene.kotlinlab.ui.blank

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ieugene.kotlinlab.database.SleepDataBaseDao
import java.lang.IllegalArgumentException

class BlankViewModelFactory(private val application: Application, private val dataBaseDao: SleepDataBaseDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlankViewModel::class.java)) {
            return BlankViewModel(application, dataBaseDao) as T
        }
        throw IllegalArgumentException("class not find")
    }

}