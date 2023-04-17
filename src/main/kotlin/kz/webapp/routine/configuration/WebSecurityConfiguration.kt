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
            //HTTP Basic authentication
            .httpBasic()
            .and()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/**").hasAnyAuthority("ADMIN")
            .requestMatchers(HttpMethod.POST, "/**").hasAnyAuthority("ADMIN")
            .requestMatchers(HttpMethod.GET, "/**").hasAnyAuthority("USER")
            .and()
            .csrf().disable()
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