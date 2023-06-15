package kz.webapp.routine.service

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ServiceFunctions {

    fun localDateToInt(localDate: LocalDate): Int {
        val day = String.format("%02d", localDate.dayOfMonth)
        val month = String.format("%02d", localDate.monthValue)
        val year = localDate.year.toString().substring(2, 4)
        return "$day$month$year".toInt()
    }
}