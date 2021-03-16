create table authors (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id))
    engine=InnoDB DEFAULT CHARSET=UTF8;

create table books (
    file_path varchar(255) not null,
    image_path varchar(255),
    name varchar(255),
    author_id bigint not null,
    primary key (file_path))
    engine=InnoDB DEFAULT CHARSET=UTF8;

create table books_genres (
    book_file_path varchar(255) not null,
    genres_id bigint not null)
    engine=InnoDB DEFAULT CHARSET=UTF8;

create table genres (
    id bigint not null auto_increment,
    description varchar(255),
    name varchar(255),
    primary key (id))
    engine=InnoDB DEFAULT CHARSET=UTF8;

create table roles (
    id bigint not null,
    name varchar(255),
    primary key (id))
    engine=InnoDB DEFAULT CHARSET=UTF8;
create table users (
    id bigint not null auto_increment,
    password varchar(255),
    username varchar(16),
    primary key (id))
    engine=InnoDB DEFAULT CHARSET=UTF8;

create table users_roles (
    user_id bigint not null,
    roles_id bigint not null,
    primary key (user_id, roles_id))
    engine=InnoDB DEFAULT CHARSET=UTF8;

alter table books
    add constraint FKfjixh2vym2cvfj3ufxj91jem7
    foreign key (author_id)
    references authors (id);

alter table books_genres
    add constraint FKd69nnd4vn4ql5pp5i0fmmr7de
    foreign key (genres_id)
    references genres (id);

alter table books_genres
    add constraint FKh1auvckx4b2jnryo8xq27xvqr
    foreign key (book_file_path)
    references books (file_path);

alter table users_roles
    add constraint FKa62j07k5mhgifpp955h37ponj
    foreign key (roles_id)
    references roles (id);

alter table users_roles
    add constraint FK2o0jvgh89lemvvo17cbqvdxaa
    foreign key (user_id)
    references users (id);