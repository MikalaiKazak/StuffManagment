drop table if exists "employees";
drop table if exists "departments";

create table if not exists "departments"
(
  "department_id"   bigserial    not null primary key,
  "department_name" varchar(100) not null
);

create table if not exists "employees"
(
  "employee_id"               bigserial          not null primary key,
  "employee_department_id"    bigint             null,
  "employee_full_name"        varchar(100)       null,
  "employee_date_of_birthday" timestamp          null,
  "employee_salary"           decimal(34, 2)     null,
  constraint "employees_department_id_fk"
    foreign key ("employee_department_id")
    references "departments" ("department_id")
    on delete cascade
    on update cascade
);