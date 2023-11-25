create table alarm
(
    alarm_config_id varchar(36)  not null
        primary key,
    is_use          bit          not null,
    name            varchar(255) not null,
    time            time(6)      not null,
    user_id         varchar(36)  not null
);

create table users
(
    user_id           varchar(36)    not null primary key,
    created_at        timestamp      not null,
    updated_at        timestamp      not null,
    identifier        text           not null,
    interested_tags   varbinary(255) not null,
    nickname          text           not null,
    profile_image_url text           not null,
    provider          varchar(255)   not null
);

create table records
(
    record_id  varchar(36) not null primary key,
    created_at timestamp   not null,
    updated_at timestamp   not null,
    color      varchar(30) not null,
    style      varchar(30) not null,
    title      text        not null,
    url        text        not null,
    user_id    varchar(36) not null
);

create table record_items
(
    record_item_id varchar(36) not null primary key,
    content        text        not null,
    is_main        bit         not null,
    record_id      varchar(36) not null
);

create table record_tags
(
    record_tag_id varchar(36) not null primary key,
    value         varchar(50) not null,
    record_id     varchar(36) not null
);


create table record_search
(
    record_search_id varchar(36) not null
        primary key,
    total_content    text        not null,
    record_id        varchar(36) not null
);

create table reports
(
    report_id   varchar(36) not null primary key,
    record_id   varchar(36) null,
    reporter_id varchar(36) null
);