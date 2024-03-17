create table chat
(
    idChat              bigint generated always as identity,
    nameChat            text                     not null,

    created_at      timestamp with time zone not null,
    created_by      text                     not null,

    primary key (idChat),
    unique (nameChat)
)
