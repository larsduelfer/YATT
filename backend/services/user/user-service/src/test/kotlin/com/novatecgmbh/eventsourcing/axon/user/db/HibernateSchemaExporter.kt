package com.novatecgmbh.eventsourcing.axon.user.db

import com.novatecgmbh.eventsourcing.axon.db.SchemaExporter
import org.h2.Driver
import org.hibernate.dialect.H2Dialect
import org.hibernate.dialect.PostgreSQLDialect
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class HibernateSchemaExporter {
    private val schemaExporter = SchemaExporter()

    @Autowired private lateinit var bean: LocalContainerEntityManagerFactoryBean

    /** Export schema for h2 database. */
    @Test
    fun exportH2Schema() {
        schemaExporter.exportSchema(
            bean,
            H2Dialect::class.java,
            Driver::class.java,
            "src/main/resources/db/schema-h2.sql"
        )
    }

    /** Export schema for mysql database. */
    @Test
    fun exportPostgresSchema() {
        schemaExporter.exportSchema(
            bean,
            PostgreSQLDialect::class.java,
            org.postgresql.Driver::class.java,
            "src/main/resources/db/schema-postgres.sql"
        )
    }
}
