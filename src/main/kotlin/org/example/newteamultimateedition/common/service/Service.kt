package org.example.newteamultimateedition.common.service

import com.github.michaelbull.result.Result


interface Service<T,E,ID> {
    fun getAll():Result<List<T>,E>
    fun getByID(id:ID): Result<T, E>
    fun save(item: T): Result<T,E>
    fun delete(id: ID): Result<T,E>
    fun update(id: ID, item: T): Result<T,E>
}