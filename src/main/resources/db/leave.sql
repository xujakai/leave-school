DROP TABLE IF EXISTS all_clazz;

CREATE TABLE all_clazz (
  clazz_name varchar(255) NOT NULL,
  PRIMARY KEY (clazz_name)
);

insert into all_clazz(clazz_name) values ('测试班1');
insert into all_clazz(clazz_name) values ('测试班2');

DROP TABLE IF EXISTS all_student;

CREATE TABLE all_student (
  stu_no varchar(10) NOT NULL,
  stu_name varchar(255) NOT NULL,
  clazz_name varchar(255) NOT NULL,
  PRIMARY KEY (stu_no)
);

DROP TABLE IF EXISTS leave_school_registration;

CREATE TABLE leave_school_registration (
  stu_no varchar(10) NOT NULL,
  stu_name varchar(255) NOT NULL,
  clazz_name varchar(255) NOT NULL,
  go int(1) NOT NULL,
  phone varchar(11),
  go_describe varchar(255) ,
  go_time datetime NULL DEFAULT NULL,
  back_time datetime NULL DEFAULT NULL,
  PRIMARY KEY (stu_no)
);




