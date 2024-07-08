
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
        diagnostics oid,
        enqueued_at timestamp(6) with time zone not null,
        last_touched timestamp(6) with time zone,
        aggregate_identifier varchar(255),
        event_identifier varchar(255) not null,
        message_type varchar(255) not null,
        meta_data oid,
        payload oid not null,
        payload_revision varchar(255),
        payload_type varchar(255) not null,
        sequence_number bigint,
        time_stamp varchar(255) not null,
        token oid,
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
        meta_data oid,
        payload oid not null,
        payload_revision varchar(255),
        payload_type varchar(255) not null,
        time_stamp varchar(255) not null,
        aggregate_identifier varchar(255) not null,
        sequence_number bigint not null,
        type varchar(255),
        primary key (global_index),
        unique (aggregate_identifier, sequence_number)
    );

    create table saga_entry (
        saga_id varchar(255) not null,
        revision varchar(255),
        saga_type varchar(255),
        serialized_saga oid,
        primary key (saga_id)
    );

    create table snapshot_event_entry (
        aggregate_identifier varchar(255) not null,
        sequence_number bigint not null,
        type varchar(255) not null,
        event_identifier varchar(255) not null unique,
        meta_data oid,
        payload oid not null,
        payload_revision varchar(255),
        payload_type varchar(255) not null,
        time_stamp varchar(255) not null,
        primary key (aggregate_identifier, sequence_number, type)
    );

    create table token_entry (
        processor_name varchar(255) not null,
        segment integer not null,
        owner varchar(255),
        timestamp varchar(255) not null,
        token oid,
        token_type varchar(255),
        primary key (processor_name, segment)
    );

    create table user_unique_key (
        identifier varchar(255) not null,
        email varchar(255) not null unique,
        external_user_id varchar(255) not null unique,
        primary key (identifier)
    );

    create table users (
        identifier varchar(255) not null,
        email varchar(255) not null,
        external_user_id varchar(255) not null,
        firstname varchar(255) not null,
        lastname varchar(255) not null,
        telephone varchar(255) not null,
        primary key (identifier)
    );

    create index IDXk45eqnxkgd8hpdn6xixn8sgft 
       on association_value_entry (saga_type, association_key, association_value);

    create index IDXgv5k1v2mh6frxuy5c0hgbau94 
       on association_value_entry (saga_id, saga_type);

    create index IDXe67wcx5fiq9hl4y4qkhlcj9cg 
       on dead_letter_entry (processing_group);

    create index IDXrwucpgs6sn93ldgoeh2q9k6bn 
       on dead_letter_entry (processing_group, sequence_identifier);
