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
             * Access to USER to todo app.
             */
//            .requestMatchers("/*").authenticated()
            .requestMatchers("/").hasAnyAuthority("USER")
            .requestMatchers("/todo").hasAnyAuthority("USER")
            .requestMatchers("/todo/**").hasAnyAuthority("USER")
            /**
             * Access to ADMIN to all links.
             */
            .requestMatchers("/**").hasAnyAuthority("ADMIN")
            .requestMatchers("/").hasAnyAuthority("ADMIN")
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