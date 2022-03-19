package com.hernanbosqued.db_unit_tests

import com.hernanbosqued.db_unit_tests.db.AppDatabase
import com.hernanbosqued.db_unit_tests.db.ParameterEntity
import com.hernanbosqued.db_unit_tests.db.toDomain

class ParameterStorageImpl(private val database: AppDatabase) : ParameterStorage {
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