package kz.webapp.routine.exception


class TaskNotExistsException(message: String?): Exception(message)

class TaskNameExistsException(message: String?): Exception(message)

class TaskClosedException(message: String?): Exception(message)

class TaskException(message: String?): Exception(message)

