#mysql
create table customer (
  id bigint(20) not null auto_increment,
  name varchar(255),
  contact varchar(255),
  telephone varchar(255),
  email varchar(255),
  remark text,
  primary key(id)
) engine=innodb default charset=utf8;

insert into customer values('1','customer1','Jack','13512345678','jack@gmail.com',null);
insert into customer values('2','customer2','Rose','13523456789','jack@gmail.com',null);
