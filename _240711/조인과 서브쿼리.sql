-- 1. 도시명 Seoul이 속한 국가 이름, 인구수, GDP를 조회

-- 일반 join
select country.name, country.population, country.GNP from country, city where city.CountryCode = country.code and city.name = 'Seoul';

-- 표준 sql
select country.name, country.population, country.GNP 
from country 
join city on city.CountryCode = country.code 
where city.name = 'Seoul';

-- subquery
select name, Population, GNP
from country
where code in (select countrycode from city where name = 'Seoul'); -- 단일행이지만 아닐 수도 있기 때문에 in을 쓰는 것이 좋음

-- 2. 아시아에 있는 국가의 도시 중 인구가 가장 많은 도시 10개를 조회하시오. 조회 항목은 국가명, 도시명, 도시 인구이다
select country.name, city.name, city.Population from country, city 
where country.code = city.countrycode and country.Continent = 'Asia' order by city.Population desc limit 10;

-- subquery 
-- city를 기준으로 subquery를 사용하면 where 조건에 Asia 부분 처리됨
-- 조인을 안쓰기 위해 최대한 where쪽에 서브쿼리 쓸 수 있는지 고민하는게 좋을듯?
select (select name from country where code = countrycode), -- select (scalar subquery)
name, population  
from city -- (inline view)
where countrycode in 
(select code from country where continent = 'Asia')  -- 조건절(nested subquery)
order by Population desc limit 10;

-- 3. 공식언어의 사용율이 50% 가 넘는 국가의 이름, 공식언어, 사용율을 조회하시오. (130)
-- 일반 join
select co.Name, cl.Language, cl.Percentage
  from country co, countrylanguage cl
 where co.code = cl.CountryCode
   and cl.IsOfficial = 'T'
   and cl.Percentage > 50.0;
-- 표준 SQL
select co.Name, cl.Language, cl.Percentage
  from country co join countrylanguage cl on co.code = cl.CountryCode
 where cl.IsOfficial = 'T'
   and cl.Percentage > 50.0;
   
-- subquery   
-- 위 2번 쿼리와 다른 점 : where 절 filtering 조건으로 country 사용 X -> select 에만 subquery 를 사용하면 된다.
select ( select name from country where code = CountryCode ), Language, Percentage
  from countrylanguage
 where IsOfficial = 'T'
   and Percentage > 50.0;
 

-- 4. 유럽의 도시 중 인구수가 가장 많은 도시의 도시명, 인구수를 조회하시오.
select name, population from city 
where population = (select max(population) from city where countrycode in (
	select code from country where Continent = 'Europe'
	)
); 

select *
  from city ci, ( select max(Population) maxPop from city where CountryCode in (
                        select code from country where continent = 'Europe'
                       )
                ) m
 where ci.population = m.maxPop;

-- sum
select sum(salary)
  from employees;
  
-- 부서별 (department_id) sum(salary)
select department_id, sum(salary)
  from employees
 group by department_id;
 
 -- 특정 부서(들)의 부서별 sum(salary)
 -- 2개 부서의 sum 만 필요한데 전체 부서의 sum 필요
select department_id, sub_salary
  from (
        select department_id, sum(salary) sub_salary
          from employees
         group by department_id
       ) sub
 where sub.department_id in (60, 100);
 
 -- case when then else end 를 이용해서 보다 효율적인 코드를 작성
 select sum( case when department_id = 60 then salary else 0 end ) sum_60,
        sum( case when department_id = 100 then salary else 0 end ) sum_100
   from employees
  where department_id in (60, 100);

select
(select sum(salary) from employees where department_id = 50) sum50,
(select avg(salary) from employees where department_id = 60) avg60,
(select max(salary) from employees where department_id = 90) max90,
(select min(salary) from employees where department_id = 90) min90
from dual;

select sum( case when department_id = 50 then salary else null end ) as sum50,
avg( case when department_id = 60 then salary else null end) as avg60,
max( case when department_id = 90 then salary else null end) as max90,
min( case when department_id = 90 then salary else null end) as min90
from employees
where department_id in (50, 60, 90);

