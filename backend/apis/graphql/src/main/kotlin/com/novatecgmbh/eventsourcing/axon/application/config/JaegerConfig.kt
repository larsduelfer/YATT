package com.novatecgmbh.eventsourcing.axon.application.config

import io.opentracing.Scope
import io.opentracing.Span
import io.opentracing.Tracer
import io.opentracing.tag.Tags
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerMapping

@Configuration
@Order(Int.MIN_VALUE)
class JaegerWebFilter : Filter {

    @Value("\${spring.application.name}") private lateinit var applicationName: String

    @Autowired private lateinit var tracer: Tracer

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val span: Span = tracer.buildSpan(applicationName).start()
        val scope: Scope = tracer.activateSpan(span)

        chain.doFilter(request, response)
        span.log(spanLogDecorator(request, span))
        scope.close()
        span.finish()
    }

    private fun spanLogDecorator(request: ServletRequest, span: Span): Map<String, Any?>? {
        val handler =
            request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE) ?: return null

        val logs: MutableMap<String, Any?> = HashMap(4)
        logs["event"] = "handle"

        val pattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)
        val patternAsString = pattern?.toString()
        if (pattern != null) {
            logs["handler"] = patternAsString
        }

        if (handler is HandlerMethod) {
            val methodName: String = handler.method.name
            logs["handler.method_name"] = handler.method.name
            span.setOperationName(methodName)
            logs["handler.class_simple_name"] = handler.beanType.getSimpleName()
        } else {
            if (pattern != null) {
                span.setOperationName(patternAsString)
            }
            logs["handler.class_simple_name"] = handler.javaClass.simpleName
        }

        val httpServletRequest = request as HttpServletRequest
        Tags.HTTP_METHOD.set(span, httpServletRequest.method)
        Tags.HTTP_URL.set(span, httpServletRequest.requestURI.toString())
        Optional.ofNullable(request.remoteAddr).ifPresent {
            Tags.PEER_HOSTNAME.set(span, request.remoteHost)
            Tags.PEER_PORT.set(span, request.remotePort)
        }

        return logs
    }
}
