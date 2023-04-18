package kz.webapp.routine.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    val accountConfiguration: AccountConfiguration,
    val passwordEncoder: BCryptPasswordEncoder
) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf().disable()
            .exceptionHandling()
            .and()
            .authorizeHttpRequests()
            /**
             * Access to Notes and Todos API calls is given to any authenticated system user.
             */
            .requestMatchers("/*").authenticated()
            /**
             * Access to User API calls is given only to Admin user.
             */
            .requestMatchers("/**").hasAnyAuthority("ADMIN")
            .and()
            .formLogin()
            .and()
            .logout()
        return httpSecurity.build()
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder)
        provider.setUserDetailsService(accountConfiguration)
        return provider
    }

}