package org.example.newteamultimateedition.common.validator

import com.github.michaelbull.result.Result

/**
 * Interfaz que representa las funciones de un validador
 */
interface Validate<T,E> {
    fun validator(item: T): Result<T,E>
}