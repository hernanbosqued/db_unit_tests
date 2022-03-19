package com.hernanbosqued.db_unit_tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val storage: ParameterStorage) : ViewModel() {
    private var data = MutableLiveData<List<Int>>()
    fun data(): LiveData<List<Int>> = data

    fun start() = viewModelScope.launch {
        data.value = storage.getAll()
    }

    fun add() = viewModelScope.launch {
        storage.add()
        data.value = storage.getAll()
    }

    fun subtract() = viewModelScope.launch {
        storage.subtract()
        data.value = storage.getAll()
    }
}

