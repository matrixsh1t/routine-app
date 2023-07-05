package kz.webapp.routine.service.impl

import kz.webapp.routine.exception.AccountException
import kz.webapp.routine.exception.TaskException
import kz.webapp.routine.model.dto.AddAccDto
import kz.webapp.routine.model.entity.AccountEntity
import kz.webapp.routine.model.entity.TaskEntity
import kz.webapp.routine.model.enums.Role
import kz.webapp.routine.repository.AccountRepo
import kz.webapp.routine.service.AccountService
import kz.webapp.routine.service.TaskService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class AccountServiceImpl(
    val accountRepo: AccountRepo,
    private val passwordEncoder: BCryptPasswordEncoder
    ): AccountService {

    val logger: Logger = LoggerFactory.getLogger(TaskService::class.java)

    override fun addAccount(addAccDto: AddAccDto) {
        val addAccountEntity = AccountEntity(
            id = 0,
            username = addAccDto.username,
            password = passwordEncoder.encode(addAccDto.password),
            email = addAccDto.email,
            role = Role.USER,
            executor = addAccDto.executor
        )

        accountEntitySaveTryCatchBlock(addAccountEntity,
            "Successfully created new account under USERNAME ${addAccDto.username}",
            "Failed to create account under USERNAME ${addAccDto.username}")
    }


    //------------------ private functions block ------------------

    //saves entity with try-catch block and makes logging
    private fun accountEntitySaveTryCatchBlock(entity: AccountEntity, msg: String, errMsg: String) {
        try {
            accountRepo.save(entity)
            logger.info(msg)
        } catch(e: TaskException) {
            logger.error(errMsg)
            logger.error(e.message)
            throw (AccountException(errMsg))
        }
    }

}