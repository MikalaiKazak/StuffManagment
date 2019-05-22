drop table if exists employees;
drop table if exists departments;

create table if not exists departments
(
  department_id   bigint primary key not null identity,
  department_name varchar(100)       not null
);

create table if not exists employees
(
  employee_id               bigint primary key not null identity,
  employee_department_id    bigint             not null,
  employee_full_name        varchar(100)       not null,
  employee_date_of_birthday date               not null,
  employee_salary           decimal(34, 2)     not null,
  constraint employees_department_id_fk foreign key (employee_department_id) references departments (department_id)
);