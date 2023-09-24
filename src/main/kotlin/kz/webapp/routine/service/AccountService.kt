package kz.webapp.routine.service

import kz.webapp.routine.model.dto.AddAccDto
import kz.webapp.routine.model.entity.AccountEntity

interface AccountService {
    fun addAccount(addAccDto: AddAccDto)
    fun showAccountList(): List<AccountEntity>
    fun deleteAccountById(id: Int)
}