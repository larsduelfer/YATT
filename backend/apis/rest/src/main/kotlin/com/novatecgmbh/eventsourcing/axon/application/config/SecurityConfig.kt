package com.novatecgmbh.eventsourcing.axon.application.config

import com.novatecgmbh.eventsourcing.axon.application.security.CustomUserAuthenticationConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfig(val userDetailsService: UserDetailsService) {

    @Bean
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .sessionManagement { it.sessionCreationPolicy(STATELESS) }
            .headers { it.httpStrictTransportSecurity { it.disable() } }
            .csrf { it.disable() }
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .exceptionHandling {}
            .oauth2ResourceServer {
                it.jwt { it.jwtAuthenticationConverter(customUserAuthenticationConverter()) }
            }
        return http.build()
    }

    @Bean
    fun customUserAuthenticationConverter(): CustomUserAuthenticationConverter =
        CustomUserAuthenticationConverter(userDetailsService)
}
