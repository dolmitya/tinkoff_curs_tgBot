create table link
(
    idLink             bigint generated always as identity,
    url            text                     not null,

    primary key (idLInk),
    unique (url),
    update_at  timestamp with time zone not null
)
