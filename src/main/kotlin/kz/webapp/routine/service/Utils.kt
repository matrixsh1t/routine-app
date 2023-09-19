package kz.webapp.routine.service

import kz.webapp.routine.model.entity.AccountEntity
import kz.webapp.routine.repository.AccountRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class Utils (var accountRepo: AccountRepo) {

    val logger: Logger = LoggerFactory.getLogger(Utils::class.java)

    fun localDateToInt(localDate: LocalDate): Int {
        val day = String.format("%02d", localDate.dayOfMonth)
        val month = String.format("%02d", localDate.monthValue)
        val year = localDate.year.toString().substring(2, 4)
        return "$day$month$year".toInt()
    }

    /** receives "userRole" or "userName" string and returns current UserName or UserRole */
    fun getCurrentUser(userData: String): String {
        val logger = LoggerFactory.getLogger(this::class.java)
        var result: String

        try {
            val authentication = SecurityContextHolder.getContext().authentication

            result = when (userData) {
                "userName" -> authentication.name
                "userRole" -> {
                    val authorities = authentication.authorities.map { it.authority }
                    authorities.joinToString(", ")
                }
                else -> throw IllegalArgumentException("Invalid user parameter: $userData")
            }
        } catch (e: Exception) {
            val msg = "Failed to get current user"
            logger.error(msg)
            logger.error(e.message)
            throw Exception(msg, e)
        }
        return result
    }

    fun getCurrentUserEntityByUserName(): AccountEntity {
        return accountRepo.findAccountEntityByUsername(getCurrentUser("userName"))!!
    }

    fun getCurrentUserIdByUserName(): Int {
        logger.error(accountRepo.findAccountIdbyUserName(getCurrentUser("userName")).toString())
        logger.error(getCurrentUser("userName"))
        return accountRepo.findAccountIdbyUserName(getCurrentUser("userName"))

    }

    fun getDateFromWeekNumber(weekNumber: Int): LocalDate {
        val firstDayOfYear = LocalDate.of(LocalDate.now().year, 1, 1)
        val firstWeek = firstDayOfYear.dayOfWeek.value
        val daysPassed = (8 - firstWeek).toLong()
        val firstMonday = firstDayOfYear.plusDays(daysPassed)
        val mondayDateOfWeekNumber = firstMonday.plusWeeks(weekNumber.toLong() - 1)
        logger.info("Week received from frontend is $weekNumber and parced to date $mondayDateOfWeekNumber ")
        return mondayDateOfWeekNumber
    }

    fun parseTheWeekOrDateFromFrontEnd(date: String?, week: String?): LocalDate {
        val dueDate: LocalDate
        // parse the date or week from controller
        if (week == "" && date!!.isNotBlank()) {
            dueDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
            logger.info("Date received from widget dueDate is $dueDate")
        } else if (date == "" && week!!.isNotBlank()) {
            dueDate = getDateFromWeekNumber(week.substring(6, 8).toInt())
        } else {
            dueDate = LocalDate.now()
            logger.info("No date or week received from widget so the date is set to today ($dueDate)")
        }
        return dueDate
    }
}