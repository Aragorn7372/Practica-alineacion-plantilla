package org.example.newteamultimateedition.common.locale



import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

private val locale= Locale.getDefault()
private val lenguaje= locale.displayLanguage
private val country = locale.displayCountry

/**
 * Trasforma un LocaDate a un [String] dandole un formato localizado.
 *
 */
    fun LocalDateTime.toLocalDateTime(): String {
        return this.format(
            DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.getDefault())
        )
    }
