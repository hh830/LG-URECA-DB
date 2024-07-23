-- truncate table (rollback 없이 전체 삭제) / delete * from ~~ 테이블에 100건의 데이터가 있다면 하나씩 삭제하며 롤백에 대응함
-- delete는 undo log, redo log를 남기며 롤백에 대비하는데 trauncate table 은 아님
--  1억 건 table truncate하면 순식간인데 1억건 table에 대해 delete를 하면 한참 걸림
-- set auto commit transactions 누르면 매번 커밋됨

create table NewBook (
    bookid integer,  -- 표준 SQL
    bookname varchar(20),
    publisher varchar(20),
    price integer
);
-- mysql workbench 생성 sql
CREATE TABLE newbook2 (
  bookid INT NULL,  -- integer 의 mysql 표현
  bookname VARCHAR(20) NULL,
  publisher VARCHAR(20) NULL,
  price INT NULL
  
  -- DBMS 마다 추가 설정이 많다. 
  -- 오라클 경험 : 20 줄 정도의 설정 ...tablespace...
);
-- 일반적으로 개발자가 테이블을 직접 생성하는 경우는 거의 없다.!!!
-- DBMS 관리 부서 또는 담당자(DBA) 가 별도로 존재하고 이 조직 또는 담당자에게 의뢰 해야 한다.
-- DB 가 시스템 전체 중 최후의 보루(이것이 망가지면 복구 X)
-- truncate table ( rollback 없이 전체 삭제 ) , delete * from ~~~ 테이블에 100 건의 데이터 1건 1건 삭제 rollback 에 대응
-- 1억 건 table truncate 한다. (순식간)   1억 건 table 에 대해 delete .....(한참 걸린다.)
-- auto commit : true 
-- 1. delete 1억건 : 1개의 작업이며, 개별 delete 가 실패할 것에 대비해 log 를 계속 남기면서 작업. 전체 작업이 마무리되면 자동으로 commit 되고 log 삭제
-- 2. delete 1건 x 1억개 : 1건 1건이 다 개별 작업이므로 delete 되면서 바로 commit
-- atuo commit : false
-- 1. delete 1억건 : 1개의 작업이며, 개별 delete 가 실패할 것에 대비해 log 를 계속 남기면서 작업. 전체 작업이 마무리어도 commit 하기 전에 log 삭제 X
-- 2. delete 1건 x 1억개 : 1건 1건이 다 개별 작업이고 개별 log 가 모두 만들어 진다. commit 되면 log 삭제
-- mysql workbench 이용한 기존 테이블 생성 스크립트 
CREATE TABLE `newbook2` (
  `bookid` int DEFAULT NULL,
  `bookname` varchar(20) DEFAULT NULL,
  `publisher` varchar(20) DEFAULT NULL,
  `price` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- R&R  현업, 개발자(코드), DBA(DBMS 운영관리), 시스템 엔지니어(서버, 네트워크, ....장비 OS)

-- PK 설정 : 별도 마지막 라인
create table NewBook (
    bookid integer,  -- PK 이므로 not null 이 기본 설정
    bookname varchar(20),
    publisher varchar(20),
    price integer,
    primary key (bookid) 
);
-- PK 설정 : 컬럼에 함께 지정
create table NewBook (
    bookid integer primary key,  -- PK 이므로 not null 이 기본 설정
    bookname varchar(20),
    publisher varchar(20),
    price integer
);
-- PK 설정 : 복합키
create table NewBook (
    bookname varchar(20),
    publisher varchar(20),
    price integer,
    primary key (bookname, publisher)
);
-- not null : 컬럼에 insert, update 할 때 null 이 들어오면 오류 발생
-- unique : 동일테이블에 중복된 값을 가진 row X <= PK 를 대체하는 용도 
--     <= 회원, 고객 테이블에 고객번호가 PK 이지만, 이메일 컬럼을 unique 로 설정하고 로그인시 이 컬럼을 사용
-- default : 값이 안들어 오면 (null) 기본값을 가지도록 미리 지정

drop table NewBook;

create table NewBook (
    bookname varchar(20) not null,
    publisher varchar(20) unique,
    price integer default 10000 check(price >= 1000),
    primary key (bookname, publisher)
);
INSERT INTO newbook (bookname, publisher) VALUES ('111', '222'); -- default 10000 사용
INSERT INTO newbook (bookname, publisher, price) VALUES ('111', '333', 20000 ); -- 값이 존재해서 20000 사용
INSERT INTO newbook (bookname, publisher, price) VALUES ('111', '444', 200 ); -- check 의 1000 보다 작아서 오류 (Check constraint...)
-- auto increment
CREATE TABLE test_table (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
) ;
insert into test_table (name) values ('aaa');
insert into test_table (name) values ('bbb');
insert into test_table (name) values ('ccc');
insert into test_table (name) values ('ddd');
insert into test_table (name) values ('ddd');
----- Foreign Key -------
create table NewCustomer(
    custid Integer Primary key,
    name varchar(40),
    address varchar(40),
    phone varchar(30)
);
create table NewOrders (
    orderid Integer,
    custid Integer not null,
    bookid integer not null,
    saleprice integer,
    orderdate date,
    primary key(orderid),
    foreign key(custid) references NewCustomer(custid)
);
-- 관계속에서 데이터의 무결성을 지키기 위해 Foreign Key 를 이용
INSERT INTO newcustomer (custid, name, address, phone) VALUES (1, '홍길동', '주소1', '010-1111-1111');
INSERT INTO newcustomer (custid, name, address, phone) VALUES (2, '이길동', '주소2', '010-2222-2222');
INSERT INTO neworders (orderid, custid, bookid, saleprice, orderdate) VALUES (1, 1, 1, 1000, '2024-01-01');
INSERT INTO neworders (orderid, custid, bookid, saleprice, orderdate) VALUES (2, 1, 2, 2000, '2024-02-02');
INSERT INTO neworders (orderid, custid, bookid, saleprice, orderdate) VALUES (3, 2, 3, 3000, '2024-03-03');
-- 자식쪽
-- 없는 custid 를 사용해서 neworders 에 insert 할 때
INSERT INTO neworders (orderid, custid, bookid, saleprice, orderdate) VALUES (4, 10, 3, 3000, '2024-03-03');
-- Error Code: 1452. Cannot add or update a child row
-- 있는  custid 를 없는 custid 로 update 하려 할 때
update neworders
   set custid = 10
 where orderid = 3;
-- Error Code: 1452. Cannot add or update a child row 
-- 부모쪽
-- FK 사용되고 있는 부모를 삭제하려고 할 때
delete from newcustomer where custid = 1;
-- Error Code: 1451. Cannot delete or update a parent row
-- neworders 의 데이터가 삭제되지 않고 custid 컬럼의 값이 null 로 변경된다.


-- ALTER
drop table NewBook;
create table NewBook(
	bookid integer, 
    bookname varchar(20),
    publisher varchar(20),
    price integer
);

alter table NewBook add isbn varchar(130);
alter table NewBook modify isbn integer;
alter table NewBook drop column isbn; -- column 생략 가능
alter table NewBook modify bookname varchar(20) not null;
desc newbook;
alter table NewBook add primary key(bookid);

-- DROP
drop table NewBook;
drop table newcustomer;

-- deletee 후 neworders에는 newcustomer(부모)를 참조하는 데이터가 없다.
delete from neworders where orderid=3;

drop table newcustomer;
-- 참조하는 부모 데이터가 없어도 drop X <-- 데이터를 보고 그 때 그 때 판단하는 게 아니고 drop 명령어 자체에 연결되어 있는 오류
-- 결론적으로 drop 할 때는 자식 테이블을 먼저 삭제 하고 부모 테이블을 나중에 삭제해야 한다.
-- neworders 테이블이 있고, 데이터가 없는 상태에서 newcustomer 는 truncate 안된다. 데이터 유무와 상관없이
-- neworders 테이블이 없으면 truncate 된다.
-- DDL <- commit, rollback 없다.
-- INSERT
-- 3-44
insert into Book (bookid, bookname, publisher, price) values ( 11, '스포츠 의학', '한솔의학서적', 90000 );
-- 전체 컬럼에 대한 입력(values)이면 컬럼명 생략
insert into Book values ( 11, '스포츠 의학', '한솔의학서적', 90000 );  -- Error Code: 1062. Duplicate entry '11' for key 'book.PRIMARY'
insert into Book values ( 12, '스포츠 의학', '한솔의학서적', 90000 );  -- 모든 컬럼에 대한 value, 순서 중요.
insert into Book (bookid, bookname, price, publisher) values ( 13, '스포츠 의학', 90000, '한솔의학서적' );
-- 3-45
insert into Book (bookid, bookname, publisher) values ( 14, '스포츠 의학', '한솔의학서적' );
-- 3-46 다른 테이블의 데이터를 select 해서 insert
insert into Book (bookid, bookname, publisher, price)
       select bookid, bookname, publisher, price from imported_book;
-- 아래 2개도 모두 가능 (테이블의 구조가 동일)
insert into Book (bookid, bookname, publisher, price)
       select * from imported_book;    
insert into Book 
       select * from imported_book;  
-- imported_book 에 컬럼을 추가       
ALTER TABLE imported_book 
ADD COLUMN isbn VARCHAR(45) NULL AFTER publisher;
insert into Book select * from imported_book;  -- column doesn't match 오류
insert into Book (bookid, bookname, publisher, price) -- column doesn't match 오류
       select * from imported_book; 
insert into Book (bookid, bookname, publisher, price) -- 문제 없다. 테이블 구조가 달라도 select 항목과 insert 항목이 맞으면 된다.
       select bookid, bookname, publisher, price from imported_book;
-- create as select : 테이블 복사 또는 임시 테이블을 만드는 경우
create table book_temp as select * from book;
select * from book_temp;
desc book;
desc book_temp;  -- pk 없다. create select 는 단순 데이터 복사
drop table book_temp;
create table book_temp as select bookname, price from book limit 10; -- 컬럼 선택, row 선택 가능
select * from book_temp;
-- UPDATE
/*
update 테이블명
   set 컬럼1 = value1 (, 컬럼2 = value2, ....)
 where 대상 row 선택
*/
select * from customer;
-- 3-47
update customer
   set address = '대한민국 부산'
 where custid = 5;
update customer
   set address = '대한민국 대전'
 where name = '박세리';
 
select @@sql_safe_updates;   -- 0:off, 1:on
set sql_safe_updates = 1; -- update, delete 작업 시 반드시 key 컬럼에 조건을 줘야 한다.
-- 3-48
-- update with subquery
select * from book;
select * from imported_book;
update book
   set publisher = (select publisher from imported_book where bookid = 21) -- single row, single col
 where bookid = 14;
update book
   set publisher = (select publisher from imported_book) -- multi row, single col -- subquery returns more than 1 rows 오류
 where bookid = 14;
update book
   set publisher = (select publisher from imported_book where bookid = 40) -- 없는 row, 오류는 발생하지 않지만 null 로 변경
 where bookid = 14;
update book
   set publisher = '임시 출판사'
 where bookid in (select bookid from imported_book);
-- DELETE
/*
delete from 테이블
 where 조건
*/
-- 3-49
delete from book
 where bookid = 11;
select * from book; 
-- 3-50
-- customer, orders FK 관계 확인
-- 데이터 없으면 추가
INSERT INTO Customer VALUES (1, '박지성', '영국 맨체스타', '000-5000-0001');
INSERT INTO Customer VALUES (2, '김연아', '대한민국 서울', '000-6000-0001'); 
INSERT INTO Customer VALUES (3, '김연경', '대한민국 경기도', '000-7000-0001');
INSERT INTO Customer VALUES (4, '추신수', '미국 클리블랜드', '000-8000-0001');
INSERT INTO Customer VALUES (5, '박세리', '대한민국 대전',  NULL);
-- mysql workbench alter table 메뉴를 이요해서 FK 추가
delete from customer; -- safe update error
set sql_safe_updates = 0;
delete from customer; -- FK 오류
select * from customer;
-- delete with subquery
delete from book
 where bookid in (select bookid from imported_book);