package kz.webapp.routine.service

import kz.webapp.routine.model.dto.AddAccDto

interface AccountService {
    fun addAccount(addAccDto: AddAccDto)
}