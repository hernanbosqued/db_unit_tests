package com.hernanbosqued.db_unit_tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hernanbosqued.db_unit_tests.db.AppDatabase
import com.hernanbosqued.db_unit_tests.db.ParameterEntity
import com.hernanbosqued.db_unit_tests.db.toDomain
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

interface ParameterStorage {
    suspend fun add()
    suspend fun subtract()
    suspend fun getAll(): List<Int>
}

class ParameterStorageImpl(private val database: AppDatabase) : ParameterStorage{
    override suspend fun add() {
        val lastValue = database.parameterDao().selectLast()?.number ?: 0
        database.parameterDao().insert(ParameterEntity(number = lastValue + 1))
    }

    override suspend fun subtract() {
        database.parameterDao().deleteLast()
    }

    override suspend fun getAll(): List<Int> {
        return database.parameterDao().selectAll().toDomain()
    }
}