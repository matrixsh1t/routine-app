package kz.webapp.routine.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
    fun filterChain(httpSecurity: HttpSecurity) : SecurityFilterChain   {
        httpSecurity
            .csrf().disable()
            .exceptionHandling()
            .and()
            .authorizeHttpRequests()
            /**
             * Access for USER and ADMIN
             */
            .requestMatchers("/bootstrap/**").authenticated()
            .requestMatchers("/img/**").authenticated()
            .requestMatchers("/css/**").authenticated()
            .requestMatchers("/").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers("/todo/**").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers("/todo/search/*").hasAnyAuthority("USER", "ADMIN")
            /**
             * Access for ADMIN only
             */
            .requestMatchers("/admin/**").hasAuthority("ADMIN")
            .and()

            .formLogin()
            .and()
            .rememberMe()
                .tokenValiditySeconds(60 * 60 * 24 * 7) // Настройка опции запомнить меня на 7 дней
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