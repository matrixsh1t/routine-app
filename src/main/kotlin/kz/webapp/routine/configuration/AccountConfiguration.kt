package kz.webapp.routine.configuration

import kz.webapp.routine.exception.AccountException
import kz.webapp.routine.repository.AccountRepo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountConfiguration(
    private val accountRepo: AccountRepo
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val account = accountRepo.findAccountEntityByUsername(username)
        if(account == null) {
            AccountException("Account with username $username is not found!")
        }
        return account!!
    }
}