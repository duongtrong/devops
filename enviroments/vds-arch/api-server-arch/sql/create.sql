
    drop table if exists example_entity;

    drop table if exists vault_samples;

    create table example_entity (
        id bigint not null auto_increment,
        created_date datetime,
        example_field varchar(255),
        primary key (id)
    );

    create table vault_samples (
        id bigint not null auto_increment,
        encrypt_field varchar(255),
        raw_field varchar(255),
        primary key (id)
    );
