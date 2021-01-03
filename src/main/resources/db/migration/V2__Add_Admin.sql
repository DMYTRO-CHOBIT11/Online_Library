insert into user (id, username, password,email, enabled)
values (1, 'admin', '$2a$10$BldynMfCAv02KicQiBMZnOTTwDFVqAOO0lvJyQVDr8ZCoKt7jj7S.','admin@gmail.com', true);

insert into user_role (user_id, roles)
values (1, 'ROLE_ADMIN');