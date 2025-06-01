package org.example.newteamultimateedition.common.locale

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class LocaleKtTest {
    val systemLang = System.getProperty("user.language")
    @Test
    @DisplayName("Localiza la fecha")
    fun toLocalDateTime(){
        val fechaExpected = LocalDateTime.of(2021, 1, 1, 1, 0, 0).format(DateTimeFormatter.ofLocalizedDateTime(
            FormatStyle.MEDIUM).withLocale(Locale.getDefault()))

        val fechaActual = LocalDateTime.of(2021, 1, 1, 1, 0, 0).toLocalDateTime()
        assertEquals(fechaExpected, fechaActual,"deberian ser iguales")
    }
}