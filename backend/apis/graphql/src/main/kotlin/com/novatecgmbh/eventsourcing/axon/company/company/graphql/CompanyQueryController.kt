package com.novatecgmbh.eventsourcing.axon.company.company.graphql

import com.novatecgmbh.eventsourcing.axon.company.company.api.CompaniesQuery
import com.novatecgmbh.eventsourcing.axon.company.company.api.CompanyId
import com.novatecgmbh.eventsourcing.axon.company.company.api.CompanyQuery
import com.novatecgmbh.eventsourcing.axon.company.company.api.CompanyQueryResult
import com.novatecgmbh.eventsourcing.axon.project.participant.api.ParticipantQueryResult
import com.novatecgmbh.eventsourcing.axon.project.project.api.CompanyIdsUserCanCreateProjectsForQuery
import java.util.concurrent.CompletableFuture
import org.axonframework.extensions.kotlin.query
import org.axonframework.extensions.kotlin.queryMany
import org.axonframework.extensions.kotlin.queryOptional
import org.axonframework.queryhandling.QueryGateway
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class CompanyQueryController(val queryGateway: QueryGateway) {

    @QueryMapping
    fun company(@Argument identifier: CompanyId): CompletableFuture<CompanyQueryResult?> =
        queryGateway
            .queryOptional<CompanyQueryResult, CompanyQuery>(CompanyQuery(identifier))
            .thenApply { optional -> optional.orElse(null) }

    // TODO: Change to BatchMapping to be more efficient
    @SchemaMapping(typeName = "Participant")
    fun company(participant: ParticipantQueryResult): CompletableFuture<CompanyQueryResult> =
        queryGateway.let { queryGateway.query(CompanyQuery(participant.companyId)) }

    @QueryMapping
    fun companies(
        @Argument userAllowedToCreateProjectFor: Boolean
    ): CompletableFuture<List<CompanyQueryResult>> {
        val companies =
            if (userAllowedToCreateProjectFor) {
                    queryGateway.queryMany<CompanyId, CompanyIdsUserCanCreateProjectsForQuery>(
                        CompanyIdsUserCanCreateProjectsForQuery()
                    )
                } else {
                    CompletableFuture.completedFuture(emptySet<CompanyId>())
                }
                .get()
        return queryGateway.queryMany<CompanyQueryResult, CompaniesQuery>(
            CompaniesQuery(companies.toSet())
        )
    }
}
