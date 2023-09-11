package kz.webapp.routine.service

import kz.webapp.routine.exception.AccountException
import kz.webapp.routine.exception.TaskException
import kz.webapp.routine.model.entity.AccountEntity
import kz.webapp.routine.repository.AccountRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ServiceFunctions (var accountRepo: AccountRepo) {

    val logger: Logger = LoggerFactory.getLogger(ServiceFunctions::class.java)
    fun localDateToInt(localDate: LocalDate): Int {
        val day = String.format("%02d", localDate.dayOfMonth)
        val month = String.format("%02d", localDate.monthValue)
        val year = localDate.year.toString().substring(2, 4)
        return "$day$month$year".toInt()
    }

    //receives "userRole" or "userName" string and returns current UserName or UserRole
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
}