-- 2 가지의 function
-- 제공되는 function, 사용자 정의 function
-- 4-1
select abs(-78), abs(78) from dual;
select abs(-78), abs(78);
-- 4-2
select round(4.875, 1); -- 소숫점 X 자리까지 반올림
-- 4-3
-- round(.., 음수) 소숫점 왼쪽(정수쪽 자리)
-- 3번째 row custid=3 항목 check
select custid, round(sum(saleprice)/count(*), -2) , avg(saleprice) 
  from orders
 group by custid;
 
-- 4-4
-- replace() 일치하는 모든 문자열을 다 바꾼다.
select bookid, bookname, replace(bookname, '야구','농구') new_bookname, publisher, price
  from book; 
-- 위 테스트를 위한 수정  
update book
   set bookname = '야구의 야구의 야구의 추억'
 where bookid = 7;
-- 원복 
update book
   set bookname = '야구의 추억'
 where bookid = 7;
-- 축구의 역사 = 한글 5x3 + space 1 = 16
-- 축구 아는 여자 = 한글 6x3 + space 2 = 20
-- utf-8 (3 byte 로 유니코드 구현) utf-16 ( 4 byte 로 유니코드 구현 )
-- euc-kr (2 byte )
-- 4-5
-- length(), char_length()
select bookname '제목', char_length(bookname), length(bookname)
  from book; 
-- blob clob 
-- 4-6
select * from customer;
-- group by 절에 alias 를 사용할 때 '성' 같은 문자열 alias X
select substr(name, 1, 1) last_name, count(*) '인원'
  from customer
 group by last_name;
 
-- 4-7
-- date(날짜) vs datetime(날짜 + 시간)
select orderid, orderdate, adddate(orderdate, interval 20 day ) -- 정확한 일별 계산
  from orders; 
  
-- 교재 227
select sysdate(), date_format(sysdate(), '%Y-%m-%d : %H:%i:%s');  
-- 4-8
select orderid, date_format(orderdate, '%Y/%m/%d') '주문일자', custid, bookid
  from orders
 where orderdate = '20240707'; -- mysql 은 가능 다른 DBMS 는 일반적으로 type 일치시켜야 한다.
select orderid, date_format(orderdate, '%Y/%m/%d') '주문일자', custid, bookid
  from orders
 where orderdate = str_to_date('20240707', '%Y%m%d'); -- 오른쪽 타입을 왼쪽 타입에 맞춘다.
 
select orderid, date_format(orderdate, '%Y/%m/%d') '주문일자', custid, bookid
  from orders
 where date_format(orderdate, '%Y%m%d') = '20240707'; -- 왼쪽 타입을 오른쪽 타입에 맞춘다.
 
 -- 동일한 결과를 낳는 위 2개의 쿼리 중 어느 것이 더 좋을까? 위의 쿼리가 좋다.
 -- 1. 위 쿼리는 str->date 로 1번 바꾸고 이후의 모든 orderdate 와 비교
 --    orderdate 컬럼에 생성된 index 에 영향을 주지 않는다. (index 를 탄다.)
 -- 2. 아래의 쿼리는 모든 row의 orderadate value 를 date->str 하고 비교 (손해가 크다)
 --    orderdate 컬럼에 생성된 index 를 타지 않는다. index 에 저장된 값과 달리 변형되기 때문
 
 -- 4-9 
 select sysdate();
 select date_format(sysdate(), '%Y %m %d %a %H %i %s');
 
 -- insert 시점 현재 시각
 insert into orders (orderid, custid, bookid, saleprice, orderdate)
   values (20, 3, 1, 13000, sysdate());
   
 insert into orders (orderid, custid, bookid, saleprice, orderdate)
   values (21, 3, 1, 13000, now());   
 insert into orders (orderid, custid, bookid, saleprice, orderdate)
   values (22, 3, 1, 13000, curdate()); 
-- sysdate() vs now()   
-- datetime type 의 default value  : current_timestamp
-- orders table 의 orderdate 를 datetime 으로 변경
 insert into orders (orderid, custid, bookid, saleprice)
   values (23, 3, 1, 13000);