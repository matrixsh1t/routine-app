package kz.webapp.routine.service.impl

import kz.webapp.routine.exception.TaskException
import kz.webapp.routine.exception.TaskNotExistsException
import kz.webapp.routine.model.dto.AddTaskDto
import kz.webapp.routine.model.dto.UpdateTaskDto
import kz.webapp.routine.model.entity.TagEntity
import kz.webapp.routine.model.entity.TaskEntity
import kz.webapp.routine.repository.AccountRepo
import kz.webapp.routine.repository.TagRepo
import kz.webapp.routine.repository.TaskRepo
import kz.webapp.routine.service.TaskService
import kz.webapp.routine.service.Utils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class TaskServiceImpl(
    val taskRepo: TaskRepo,
    val accountRepo: AccountRepo,
    val tagRepo: TagRepo,
    val utils: Utils
    ): TaskService {

    val logger: Logger = LoggerFactory.getLogger(TaskService::class.java)

    // active tasks for today of current user
    override fun showTodaysTasks(): List<TaskEntity> {
        val currentUser = utils.getCurrentUser("userName")
        return taskRepo.findAllTodaysTasksOfCurrentUser(currentUser).ifEmpty {
            val msg = "There are no tasks for today found"
            logger.error(msg)
            ArrayList()
        }
    }

    // active tasks for tomorrow of current user
    override fun showTomorrowsTasks(): List<TaskEntity> {
        val currentUser = utils.getCurrentUser("userName")
        return taskRepo.findAllTomorrowsTasksOfCurrentUser(currentUser).ifEmpty {
            val msg = "There are no tasks for tomorrow found"
            logger.error(msg)
            ArrayList()
        }
    }

    // active tasks for next week of current user
    override fun showNextWeeksTasks(): List<TaskEntity> {
        val currentUser = utils.getCurrentUser("userName")
        return taskRepo.findAllNextWeeksTasksOfCurrentUser(currentUser).ifEmpty {
            val msg = "There are no tasks for next week found"
            logger.error(msg)
            ArrayList()
        }
    }

    // active tasks for next month of current user
    override fun showNextMonthsTasks(): List<TaskEntity> {
        val currentUser = utils.getCurrentUser("userName")
        return taskRepo.findAllNextMonthsTasksOfCurrentUser(currentUser).ifEmpty {
            val msg = "There are no tasks for next month found"
            logger.error(msg)
            ArrayList()
        }
    }

    // all active tasks of all users (for Admin)
    override fun showAllActiveTasks(): List<TaskEntity> {
        return taskRepo.findAllTasksByStatus("a").ifEmpty {
            val msg = "There are no active tasks for any user found"
            logger.error(msg)
            ArrayList()
        }
    }

    // all closed tasks of all users (for Admin)
    override fun showAllClosedTasks(): List<TaskEntity> {
        return taskRepo.findAllTasksByStatus("x").ifEmpty {
            val msg = "There are no closed tasks for any user found"
            logger.error(msg)
            ArrayList()
        }
    }

    //all active tasks of current user
    override fun showAllActiveTasksOfCurrentUser(): List<TaskEntity> {
        val currentUser = utils.getCurrentUser("userName")
        return taskRepo.findAllByAccountIdUsernameAndStatusEquals(currentUser, "a").ifEmpty {
            val msg = "There are no active tasks found of $currentUser"
            logger.error(msg)
            ArrayList()
        }
    }

    //all closed tasks of current user
    override fun showAllClosedTasksOfCurrentUser(): List<TaskEntity> {
        val currentUser = utils.getCurrentUser("userName")
        return taskRepo.findAllByAccountIdUsernameAndStatusEquals(currentUser, "x").ifEmpty {
            val msg = "There are no closed tasks found of $currentUser"
            logger.error(msg)
            ArrayList()
        }
    }

    // all tasks of current user (closed and active)
    override fun showAllTasksOfCurrentUser(): List<TaskEntity> {
        val currentUser = utils.getCurrentUser("userName")
        return taskRepo.findAllByAccountIdUsernameOrderByDueDate(currentUser).ifEmpty {
            val msg = "There are no active tasks found"
            logger.error(msg)
            ArrayList()
        }
    }

    // all tasks of all users(closed, active, cancelled)
    override fun showAllTasks(): List<TaskEntity> {
        return taskRepo.findAllByOrderByAccountIdUsername().ifEmpty {
            val msg = "There are no tasks for all users found at all"
            logger.error(msg)
            ArrayList()
        }
    }

    override fun addTask(addTaskDto: AddTaskDto) {
        // parse the date or week from controller
        val dueDate: LocalDate = utils.parseTheWeekOrDateFromFrontEnd(addTaskDto.dueDate, addTaskDto.dueWeek)
        val tagsToAdd = addTagsToTask(addTaskDto.selectedTags)

        if(addTaskDto.account == "All") {
            logger.info("Saving task for all users")
            val accounts = accountRepo.findAll()

            for (account in accounts) {
                val addTaskEntity = TaskEntity(
                    taskId = 0,
                    task = addTaskDto.task,
                    comment = addTaskDto.comment,
                    city = addTaskDto.city,
                    status = "a",
                    createDate = LocalDate.now(),
                    dueDate = dueDate,
                    closeDate = null,
                    accountId = account,
                    tags = tagsToAdd,
                )

                //save entity with separate function of try-catch block
                entitySaveTryCatchBlock(addTaskEntity,
                    "Successfully created new task ${addTaskEntity.task} for user ${account.username}",
                    "Failed to create task ${addTaskEntity.task} for user ${account.username}")
            }
        } else {
            val account = accountRepo.findAccountEntityByUsername(addTaskDto.account
                .ifEmpty { utils.getCurrentUser("userName") })!!

            logger.info("Account from frontend: ${addTaskDto.account}")
            logger.info("Account parsed ${account.username}")

            val addTaskEntity = TaskEntity(
                taskId = 0,
                task = addTaskDto.task,
                comment = addTaskDto.comment,
                city = addTaskDto.city,
                status = "a",
                createDate = LocalDate.now(),
                dueDate = dueDate,
                closeDate = null,
                accountId = account,
                tags = tagsToAdd,
            )

            entitySaveTryCatchBlock(addTaskEntity,
                "Successfully created new task ${addTaskEntity.task}",
                "Failed to create task ${addTaskEntity.task}")
        }

    }

    override fun deleteTaskById(id: Int) {
        val task = taskRepo.findByIdOrNull(id)

        if (task != null) {
            taskRepo.deleteById(id)
            logger.info("The task with ID $id was successfully deleted")
        } else {
            val msg = "No task with id $id found"
            logger.error(msg)
            throw TaskNotExistsException(msg)
        }
    }

    override fun updateTask(id: Int, updateTaskDto: UpdateTaskDto) {
        val updateTaskEntity = taskRepo.findByIdOrNull(id)
        // parse the date or week from controller
        val dueDate: LocalDate = utils.parseTheWeekOrDateFromFrontEnd(updateTaskDto.dueDate, updateTaskDto.dueWeek)
        // get tags from frontend and prepare to save
        val tagsToAdd = addTagsToTask(updateTaskDto.selectedTags)

        if (updateTaskEntity != null) {
            val updateTaskEntity = TaskEntity(
                taskId = id,
                task = updateTaskDto.task,
                comment = updateTaskDto.comment,
                city = updateTaskDto.city,
                createDate = updateTaskDto.createDate,
                dueDate = dueDate,
                closeDate = null,
                status = updateTaskDto.status,
                accountId  = accountRepo.findAccountEntityByUsername(updateTaskDto.userName)!!,
                tags = tagsToAdd
            )

            //saves entity with try-catch and logs
            entitySaveTryCatchBlock(
                updateTaskEntity,
                "Task ${updateTaskEntity.taskId} is updated",
                "Failed to update task with id ${updateTaskEntity.taskId}"
            )
        }
    }

    override fun moveTaskToAnotherDate(id: Int, period: String) {
        val updateTimeEntity = taskRepo.findByIdOrNull(id)
        var newDate = LocalDate.now()

        // add period to change the date to make it monday
        when(period) {
            "day" -> {
                newDate = if (LocalDate.now().dayOfWeek.value >= 5) {
                    LocalDate.now().plusDays(8-LocalDate.now().dayOfWeek.value.toLong())
                } else LocalDate.now().plusDays(1)
            }
            "week" -> newDate = LocalDate.now().plusDays(8-LocalDate.now().dayOfWeek.value.toLong())
            "month" -> newDate = LocalDate.now().plusDays(29-LocalDate.now().dayOfWeek.value.toLong())
        }

        if (updateTimeEntity != null) {
            val updateTimeEntity = TaskEntity(
                taskId = id,
                task = updateTimeEntity.task,
                comment = updateTimeEntity.comment,
                city = updateTimeEntity.city,
                dueDate = newDate,
                createDate = updateTimeEntity.createDate,
                closeDate = LocalDate.now(),
                status = updateTimeEntity.status,
                accountId = utils.getCurrentUserEntityByUserName()
            )

            // saves entity with try-catch and logs
            entitySaveTryCatchBlock(updateTimeEntity,
                "Task ${updateTimeEntity.taskId} is moved to tomorrow",
                "Failed to update task with id ${updateTimeEntity.taskId}")
        }
    }

    override fun findTaskById(id: Int): TaskEntity? {
        return taskRepo.findByIdOrNull(id)
    }

    override fun closeTask(id: Int) {
        val closeTaskEntity = taskRepo.findByIdOrNull(id)

        if (closeTaskEntity != null) {
            val closeTaskEntity = TaskEntity(
                taskId = id,
                task = closeTaskEntity.task,
                comment = closeTaskEntity.comment,
                city = closeTaskEntity.city,
                status = "x",
                createDate = closeTaskEntity.createDate,
                dueDate = closeTaskEntity.dueDate,
                closeDate = LocalDate.now(),
                accountId = closeTaskEntity.accountId
                )

            // saves entity with try-catch and logs
            entitySaveTryCatchBlock(closeTaskEntity,
                "Task ${closeTaskEntity.taskId} is closed",
                "Failed to closed task with id ${closeTaskEntity.taskId}")
        }
    }

    override fun getListOfResponsiblesFromDb(): List<String> {
        return accountRepo.findAllResponsiblesFromDb()
    }

    // all tasks which have searchstring in Task or Comment cell of admin or other users
    override fun searchInDb(searchString: String): List<TaskEntity> {
        val currentUser = utils.getCurrentUser("userName")
        return if (currentUser == "admin") {
            taskRepo.findAllTasksAsSearchResult(searchString)
                .ifEmpty { ArrayList() }.also { logger.info("Searching for prompt: $searchString")}
        } else {
            taskRepo.findAllTasksAsSearchResultOfCurrentUser(searchString, currentUser)
                .ifEmpty { ArrayList() }.also { logger.info("Searching for prompt: $searchString")}
        }

    }

    override fun activateTask(id: Int) {
        val activateTaskEntity = taskRepo.findByIdOrNull(id)

        if (activateTaskEntity != null) {
            val activateTaskEntity = TaskEntity(
                taskId = id,
                task = activateTaskEntity.task,
                comment = activateTaskEntity.comment,
                city = activateTaskEntity.city,
                status = "a",
                createDate = activateTaskEntity.createDate,
                dueDate = activateTaskEntity.dueDate,
                closeDate = null,
                accountId = activateTaskEntity.accountId
            )

            // saves entity with try-catch and logs
            entitySaveTryCatchBlock(activateTaskEntity,
                "Task ${activateTaskEntity.taskId} is activated",
                "Failed to activate task with id ${activateTaskEntity.taskId}")
        }
    }

    // all tasks by TagName of all users (for Admin)
    override fun getAllTasksByTagName(tagName: String): List<TaskEntity> {
        return taskRepo.findAllByTagsTagNameAndStatusEquals(tagName, "a").ifEmpty {
            val msg = "There are no tasks for tag $tagName for all users found at all"
            logger.error(msg)
            ArrayList()
        }
    }

    // all active tasks by TagName of current user
    override fun getAllTasksByTagNameOfCurrentUser(tagName: String): List<TaskEntity> {
        val currentUser = utils.getCurrentUser("userName")
        return taskRepo
            .findAllTasksByTagOfCurrentUser(currentUser, tagName).ifEmpty {
            val msg = "There are no tasks for tag $tagName for user $currentUser found at all"
            logger.error(msg)
            ArrayList()
        }
    }

        //-------------------------------------------------------------
        //------------------ private functions block ------------------
        //-------------------------------------------------------------

    /** saves entity with try-catch block and makes logging */
    private fun entitySaveTryCatchBlock(entity: TaskEntity, msg: String, errMsg: String) {
        try {
            taskRepo.save(entity)
            logger.info(msg)
        } catch(e: TaskException) {
            logger.error(errMsg)
            logger.error(e.message)
            throw (TaskException(errMsg))
        }
    }

    private fun addTagsToTask(tagNames: List<String>): Set<TagEntity> {
        var tagsToSave: Set<TagEntity> = HashSet()
        var tagEntity: TagEntity?

        logger.info("tagNames checked in frontend: $tagNames")

        if (tagNames.isEmpty()) {
            return tagsToSave
        } else {
                for (tagName in tagNames) {
                    logger.info("adding tagEntity with tagName: $tagName")

                    tagEntity = tagRepo.findByTagName(tagName)
                    logger.info("initial tagsToSave value: $tagsToSave")

                    tagsToSave = tagsToSave + tagEntity
                    logger.info("tagsToSave after iteration: $tagsToSave")
                }
            }
        return tagsToSave
    }
}