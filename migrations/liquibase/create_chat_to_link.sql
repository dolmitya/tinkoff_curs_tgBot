create table ChatLink
(
    idChat bigint references chat(idChat),
    idLink bigint references link(idLink),
    constraint chat_link_pkey primary key (idChat,idLink)
)
