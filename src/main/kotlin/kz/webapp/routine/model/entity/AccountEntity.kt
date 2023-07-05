package kz.webapp.routine.model.entity

import jakarta.persistence.*
import kz.webapp.routine.model.enums.Role
import org.hibernate.annotations.DynamicUpdate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


@Entity
@Table(name = "account")
@DynamicUpdate
class AccountEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", sequenceName = "account_account_id_seq", allocationSize = 1)
    @Column(name = "account_id")
    val id: Int?,

    @Column(name = "username", unique = true, nullable = false)
    private val username: String,

    @Column(name = "password", nullable = false)
    private val password: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    val role: Role,

    @Column(name = "executor")
    val executor: String,

): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authority = SimpleGrantedAuthority(role.name)
        return Collections.singleton(authority)
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}