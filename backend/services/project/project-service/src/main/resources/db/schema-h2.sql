
    create sequence association_value_entry_seq start with 1 increment by 50;

    create sequence domain_event_entry_seq start with 1 increment by 50;

    create table association_value_entry (
        id bigint not null,
        association_key varchar(255) not null,
        association_value varchar(255),
        saga_id varchar(255) not null,
        saga_type varchar(255),
        primary key (id)
    );

    create table dead_letter_entry (
        dead_letter_id varchar(255) not null,
        cause_message varchar(1023),
        cause_type varchar(255),
        diagnostics blob,
        enqueued_at timestamp(6) with time zone not null,
        last_touched timestamp(6) with time zone,
        aggregate_identifier varchar(255),
        event_identifier varchar(255) not null,
        message_type varchar(255) not null,
        meta_data blob,
        payload blob not null,
        payload_revision varchar(255),
        payload_type varchar(255) not null,
        sequence_number bigint,
        time_stamp varchar(255) not null,
        token blob,
        token_type varchar(255),
        type varchar(255),
        processing_group varchar(255) not null,
        processing_started timestamp(6) with time zone,
        sequence_identifier varchar(255) not null,
        sequence_index bigint not null,
        primary key (dead_letter_id),
        unique (processing_group, sequence_identifier, sequence_index)
    );

    create table domain_event_entry (
        global_index bigint not null,
        event_identifier varchar(255) not null unique,
        meta_data blob,
        payload blob not null,
        payload_revision varchar(255),
        payload_type varchar(255) not null,
        time_stamp varchar(255) not null,
        aggregate_identifier varchar(255) not null,
        sequence_number bigint not null,
        type varchar(255),
        primary key (global_index),
        unique (aggregate_identifier, sequence_number)
    );

    create table participant (
        identifier varchar(255) not null,
        company_id varchar(255) not null,
        company_name varchar(255),
        project_id varchar(255) not null,
        user_email varchar(255),
        user_first_name varchar(255),
        user_id varchar(255) not null,
        user_last_name varchar(255),
        user_telephone varchar(255),
        version bigint not null,
        primary key (identifier)
    );

    create table participant_unique_key (
        identifier varchar(255) not null,
        company_id varchar(255) not null,
        project_id varchar(255) not null,
        user_id varchar(255) not null,
        primary key (identifier),
        constraint participant_unique_key_constraint unique (project_id, company_id, user_id)
    );

    create table project_acls (
        aggregate_identifier varchar(255) not null,
        aggregate_type enum ('COMPANY','PROJECT') not null,
        permission enum ('ACCESS_PROJECT','CREATE_PROJECT') not null,
        identifier varchar(255) not null,
        primary key (aggregate_identifier, aggregate_type, permission, identifier)
    );

    create table project_by_task_lookup (
        identifier varchar(255) not null,
        project_id varchar(255) not null,
        primary key (identifier)
    );

    create table project_details (
        identifier varchar(255) not null,
        all_tasks_count bigint not null,
        completed_tasks_count bigint not null,
        deadline date not null,
        name varchar(255) not null,
        planned_start_date date not null,
        planned_tasks_count bigint not null,
        started_tasks_count bigint not null,
        version bigint not null,
        primary key (identifier)
    );

    create table projects (
        identifier varchar(255) not null,
        actual_end_date date,
        company_name varchar(255),
        company_id varchar(255) not null,
        deadline date not null,
        name varchar(255) not null,
        planned_start_date date not null,
        status enum ('DELAYED','ON_TIME') not null,
        version bigint not null,
        primary key (identifier)
    );

    create table root_context_id_mapping (
        aggregate_identifier varchar(255) not null,
        aggregate_type varchar(255) not null,
        root_context_id varchar(255) not null,
        primary key (aggregate_identifier, aggregate_type, root_context_id)
    );

    create table saga_entry (
        saga_id varchar(255) not null,
        revision varchar(255),
        saga_type varchar(255),
        serialized_saga blob,
        primary key (saga_id)
    );

    create table snapshot_event_entry (
        aggregate_identifier varchar(255) not null,
        sequence_number bigint not null,
        type varchar(255) not null,
        event_identifier varchar(255) not null unique,
        meta_data blob,
        payload blob not null,
        payload_revision varchar(255),
        payload_type varchar(255) not null,
        time_stamp varchar(255) not null,
        primary key (aggregate_identifier, sequence_number, type)
    );

    create table task_schedule_projection (
        identifier varchar(255) not null,
        end_date date not null,
        project_id varchar(255) not null,
        start_date date not null,
        primary key (identifier)
    );

    create table task_todos (
        task_identifier varchar(255) not null,
        description varchar(255) not null,
        is_done boolean not null,
        identifier varchar(255),
        constraint UK_TaskTodos_Identifier unique (identifier, task_identifier)
    );

    create table tasks (
        identifier varchar(255) not null,
        assignee_company_name varchar(255),
        assignee_first_name varchar(255),
        assignee_last_name varchar(255),
        description varchar(255),
        end_date date not null,
        name varchar(255) not null,
        participant_id varchar(255),
        project_id varchar(255) not null,
        start_date date not null,
        status tinyint not null check (status between 0 and 2),
        version bigint not null,
        primary key (identifier)
    );

    create table token_entry (
        processor_name varchar(255) not null,
        segment integer not null,
        owner varchar(255),
        timestamp varchar(255) not null,
        token blob,
        token_type varchar(255),
        primary key (processor_name, segment)
    );

    create index IDXk45eqnxkgd8hpdn6xixn8sgft 
       on association_value_entry (saga_type, association_key, association_value);

    create index IDXgv5k1v2mh6frxuy5c0hgbau94 
       on association_value_entry (saga_id, saga_type);

    create index IDXe67wcx5fiq9hl4y4qkhlcj9cg 
       on dead_letter_entry (processing_group);

    create index IDXrwucpgs6sn93ldgoeh2q9k6bn 
       on dead_letter_entry (processing_group, sequence_identifier);

    alter table if exists task_todos 
       add constraint FK_TaskTodos_TaskIdentifier 
       foreign key (task_identifier) 
       references tasks;

create index IX_TaskTodo_TaskIden on task_todos (task_identifier);

