INSERT INTO user (id, username, password, enabled, first_name, last_name, phone) VALUES (1, 'user1', '$2a$04$SxCC9bFl/U5YJKkgG72m2ep30nFnCvw/FLLG5XAPT/PufSFOv7u/C', true,  'Jan', 'Kowalski', '506225785');
INSERT INTO user (id, username, password, enabled, first_name, last_name, phone) VALUES (2, 'user2', '$2a$04$dG89YR14ZdDKjJgDUQwzCeT7wskKrNyJrb8ucrVjk5zLi67aQvnhC', true,  'Jan', 'Kowalski', '506225785');
INSERT INTO user (id, username, password, enabled, first_name, last_name, phone) VALUES (3, 'user3', '$2a$04$1gMKwOaDsSE9lDWJ7qmsB.moaXStggCOKqFIvvUW5DXKHCR1aExEq', true,  'Jan', 'Kowalski', '506225785');

INSERT INTO user_role (id, username, role) VALUES (1, 'user1', 'ROLE_ADMIN');
INSERT INTO user_role (id, username, role) VALUES (2, 'user2', 'ROLE_USER');
INSERT INTO user_role (id, username, role) VALUES (3, 'user3', 'ROLE_USER');