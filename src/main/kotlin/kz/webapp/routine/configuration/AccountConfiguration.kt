package kz.webapp.routine.configuration

import kz.webapp.routine.exception.AccountNotExistsException
import kz.webapp.routine.repository.AccountRepo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountConfiguration(
    private val accountRepo: AccountRepo
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        try {
            val user = accountRepo.findAccountEntityByUsername(username)
            if (user == null) {
                throw AccountNotExistsException("Account with username $username not found!")
            }

            return user
        } catch (e: AccountNotExistsException) {
            throw AccountNotExistsException("Account with username $username not found!")
        }
    }
}