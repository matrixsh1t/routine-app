package kz.webapp.routine.repository

import kz.webapp.routine.model.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepo: JpaRepository<AccountEntity, Int> {

    // get account by username
    fun findAccountEntityByUsername(username: String?): AccountEntity?

    // get account by email
    fun findByEmail(email: String): AccountEntity?

    // check if account with username exists
    fun existsAccountEntityByUsername(username: String): Boolean

//    @Query("SELECT a.username FROM AccountEntity a ORDER BY a.username")
    @Query(nativeQuery = true, value = "SELECT username FROM account ORDER BY username;")
    fun findAllUserNamesFromDb(): List<String>

    // find all respinsibles (usernames) from DB
    @Query(nativeQuery = true, value = "SELECT DISTINCT username FROM account;" )
    fun findAllResponsiblesFromDb(): List<String>

    @Query("SELECT a.id FROM AccountEntity a WHERE a.username = :userName")
    fun findAccountIdbyUserName(userName: String): Int



//    @Query("SELECT a.responsible FROM account a WHERE a.userName = :userName")
//    fun findResponsibleByUsername(@Param("userName") userName: String) : String

//    fun findAccountEntityById(): AccountEntity?
}