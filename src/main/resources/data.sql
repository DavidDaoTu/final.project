insert into student(id, student_name, address, birthdate)
values(2001, 'Ranga', 'India', '01-Jan-1993');
insert into student(id, student_name, address, birthdate)
values(2002, 'Adam', 'USA', '01-Jan-1995');
insert into student(id, student_name, address, birthdate)
values(2003, 'Jane', 'French', '01-Jan-1991');

insert into course(id, course_name, start_date, end_date)
values(3001, 'History', '2021-11-01', '2022-02-19');
insert into course(id, course_name, start_date, end_date)
values(3002, 'Science', '2021-11-01', '2022-02-19');
insert into course(id, course_name, start_date, end_date)
values(3003, 'Math', '2021-11-01', '2022-02-19');

insert into student_course(student_id, course_id, score)
values(2001, 3001, 7);
insert into student_course(student_id, course_id, score)
values(2001, 3002, 8);
insert into student_course(student_id, course_id, score)
values(2001, 3003, 9);
insert into student_course(student_id, course_id, score)
values(2002, 3001, 7);
insert into student_course(student_id, course_id, score)
values(2002, 3002, 3);
insert into student_course(student_id, course_id, score)
values(2003, 3003, 1);