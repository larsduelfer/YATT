package com.novatecgmbh.eventsourcing.axon.project.project.web.dto

import com.novatecgmbh.eventsourcing.axon.company.company.api.CompanyId
import com.novatecgmbh.eventsourcing.axon.project.project.api.CreateProjectCommand
import com.novatecgmbh.eventsourcing.axon.project.project.api.ProjectId
import java.time.LocalDate

data class CreateProjectDto(
    val name: String,
    val startDate: LocalDate,
    val deadline: LocalDate,
    val companyId: CompanyId
) {
    fun toCommand(projectId: ProjectId) =
        CreateProjectCommand(projectId, name, startDate, deadline, companyId)
}
