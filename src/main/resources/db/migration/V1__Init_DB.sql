create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values ( 1 );
create table author (id bigint not null, birthday date, fio varchar(255), primary key (id));
create table author_book (author_id bigint not null, book_id bigint not null, primary key (author_id, book_id));
create table book (book_id bigint not null, content varchar(255), descr varchar(255), image varchar(255), isbn integer, name varchar(255), publish_year integer, genre_id bigint, publisher_id bigint, primary key (book_id));
create table genre (id bigint not null, name varchar(255), primary key (id));
create table genre_books (genre_id bigint not null, books_book_id bigint not null, primary key (genre_id, books_book_id));
create table publisher (id bigint not null, name varchar(255), primary key (id));
create table user (id bigint not null, email varchar(255), enabled bit not null, password varchar(255), username varchar(255), primary key (id));
create table user_role (user_id bigint not null, roles varchar(255));
alter table book add constraint UK_ehpdfjpu1jm3hijhj4mm0hx9h unique (isbn);
alter table genre add constraint UK_ctffrbu4484ft8dlsa5vmqdka unique (name);
alter table user add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);
alter table author_book add constraint FKn8665s8lv781v4eojs8jo3jao foreign key (book_id) references book (book_id);
alter table author_book add constraint FKg7j6ud9d32ll232o9mgo90s57 foreign key (author_id) references author (id);
alter table book add constraint FKm1t3yvw5i7olwdf32cwuul7ta foreign key (genre_id) references genre (id);
alter table book add constraint FKgtvt7p649s4x80y6f4842pnfq foreign key (publisher_id) references publisher (id);
alter table user_role add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id);