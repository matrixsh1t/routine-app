package kz.webapp.routine.repository

import kz.webapp.routine.model.entity.AccountEntity
import org.postgresql.core.NativeQuery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepo: JpaRepository<AccountEntity, Int> {

    //---------get account by username
    fun findAccountEntityByUsername(username: String?): Optional<AccountEntity>

    //---------get account by email
    fun findByEmail(email: String): AccountEntity?

    //---------check if account with username exists
    fun existsAccountEntityByUsername(username: String): Boolean

//    @Query("SELECT a.username FROM AccountEntity a ORDER BY a.username")
    @Query(nativeQuery = true, value = "SELECT username FROM account ORDER BY username;")
    fun findAllUserNamesFromDb(): List<String>

    @Query(nativeQuery = true, value = "SELECT DISTINCT executor FROM account;" )
    fun findAllResponsiblesFromDb(): List<String>

//    @Query("SELECT a.responsible FROM account a WHERE a.userName = :userName")
//    fun findResponsibleByUsername(@Param("userName") userName: String) : String
}