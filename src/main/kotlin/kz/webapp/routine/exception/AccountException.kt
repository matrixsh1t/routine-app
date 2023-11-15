package kz.webapp.routine.exception


class AccountNotExistsException(message: String?): Exception(message)

class AccountEmailExistsException(message: String?): Exception(message)

class AccountException(message: String?): Exception(message)

