package com.novatecgmbh.eventsourcing.axon.company.employee.query

import com.novatecgmbh.eventsourcing.axon.company.employee.api.EmployeeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface EmployeeProjectionRepository : JpaRepository<EmployeeProjection, EmployeeId>