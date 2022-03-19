package com.hernanbosqued.db_unit_tests

interface ParameterStorage {
    suspend fun add()
    suspend fun subtract()
    suspend fun getAll(): List<Int>
}