package com.novatecgmbh.eventsourcing.axon.application.config

import com.novatecgmbh.eventsourcing.axon.application.security.CustomUserAuthenticationConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.server.support.AbstractAuthenticationWebSocketInterceptor
import org.springframework.graphql.server.support.BearerTokenAuthenticationExtractor
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.web.SecurityFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.Context
import reactor.util.context.ContextView

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfig(
    val userDetailsService: UserDetailsService,
    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}") val issuer: String
) {

    @Bean
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .sessionManagement { it.sessionCreationPolicy(STATELESS) }
            .headers { it.httpStrictTransportSecurity { it.disable() } }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/wsgraphql", "/graphql/schema/**", "/graphiql/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
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

    @Bean
    fun authenticationInterceptor(): CustomAuthenticationWebSocketInterceptor =
        CustomAuthenticationWebSocketInterceptor(userDetailsService, issuer)
}

class CustomAuthenticationWebSocketInterceptor(
    private val userDetailsService: UserDetailsService,
    private val issuer: String
) : AbstractAuthenticationWebSocketInterceptor(BearerTokenAuthenticationExtractor()) {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authConverter = CustomUserAuthenticationConverter(userDetailsService)
        val jwt =
            JwtDecoders.fromIssuerLocation<JwtDecoder>(issuer)
                .decode((authentication.principal as String).replace("Bearer ", ""))
        val user = authConverter.convert(jwt)

        return Mono.just(user)
    }

    override fun getContextToWrite(securityContext: SecurityContext): ContextView {
        val key = SecurityContext::class.java.name // match SecurityContextThreadLocalAccessor key
        return Context.of(key, securityContext)
    }
}
