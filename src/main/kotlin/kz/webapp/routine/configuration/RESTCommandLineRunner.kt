package kz.webapp.routine.configuration

import kz.webapp.routine.model.entity.AccountEntity
import kz.webapp.routine.model.enums.Role
import kz.webapp.routine.repository.AccountRepo
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component


@Component
class RESTCommandLineRunner(
    private val accountRepo: AccountRepo,
    private val passwordEncoder: BCryptPasswordEncoder
): CommandLineRunner {
    override fun run(vararg args: String?) {
        if (!accountRepo.existsByUsername("admin")) {
            accountRepo.saveAndFlush(AccountEntity(
                id = null,
                username = "admin",
                password = passwordEncoder.encode("admin"),
                email = "email",
                role = Role.ADMIN
            )
            )
        }
    }
}