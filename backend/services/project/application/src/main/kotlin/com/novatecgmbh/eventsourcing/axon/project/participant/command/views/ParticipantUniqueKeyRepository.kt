package com.novatecgmbh.eventsourcing.axon.project.participant.command.views

import com.novatecgmbh.eventsourcing.axon.company.company.api.CompanyId
import com.novatecgmbh.eventsourcing.axon.project.participant.api.ParticipantId
import com.novatecgmbh.eventsourcing.axon.project.project.api.ProjectId
import com.novatecgmbh.eventsourcing.axon.user.api.UserId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParticipantUniqueKeyRepository :
    JpaRepository<ParticipantUniqueKeyProjection, ParticipantId> {
    fun existsByProjectIdAndCompanyIdAndUserId(
        projectId: ProjectId,
        companyId: CompanyId,
        userId: UserId
    ): Boolean
}
