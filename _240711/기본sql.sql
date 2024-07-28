use world;
-- 1. country table의 모든 자료를 조회하시오(239)
SELECT * FROM world.country;

-- 2. country table의 전체 건수를 구하시오(1)
select count(*) from world.country; -- 239건

-- 3. country table에서 국가 code가 FRA인 자료를 조회하시오 (1)
select * from world.country where code = 'FRA';

-- 4. country table에서 대륙(continent)가 africa 또는 europe인 자료를 조회하시오. (104)
select * from world.country where Continent in ('Africa', 'Europe'); -- or 가능

-- 5. country table에서 독립일(IndepYear)이 없는 나라의 자료를 조회하시오. (47)
select * from world.country where IndepYear is null; -- = null 아님 조심하기

-- 6. country table에서 모든 독립일을 중복없이 조회하시오. (89)
select distinct indepyear from country; -- null 포함됨: distinct는 null을 포함함

select distinct indepyear from country where IndepYear is not null; -- null을 포함하지 않음 (88)

-- 7. country table에서 인구(Population)이 1,000,000보다 크고 수명 예상(LifeExpectancy)이 70살 이상인 자료를 조회하시오 (66)
select * from country where Population > 1000000 and LifeExpectancy >= 70;

-- 8. country table 에서 이전 gnp(GNPOld) 대비 gnp 가 1000 이상 증가한 국가의 이름과 gnp, GNPOld, 증가한 GNP 를 조회하시오.
-- 이 때 증가한 GNP 를 GNPDiff 로 alias 를 주세요.
select name, gnp, (gnp-GNPOld) as GNPDiff from country where (gnp - GNPOld) >= 1000;

-- 9. 위 데이터를 GNPDiff로 내림차순 정렬
select name, gnp, (gnp-GNPOld) as GNPDiff from country where (gnp - GNPOld) >= 1000 order by GNPDiff desc;

-- 10. country table에서 GNP가 100미만 또는 100000 초과인 자료를 조회하시오. (68)
select * from country where GNP < 100 or GNP > 100000;

-- 11. country table GNP가 100 초과하고 100000 미만인 자료를 조회하시오. (170)
select * from country where GNP > 100 and GNP < 100000;

-- 12. country table에서 GNP가 100이상 100000이하인 자료를 조회하시오 (171)
select * from country where GNP >= 100 and GNP <= 100000; -- between 100 and 100000

-- 13. 위 11, 12 차이를 만드는 나라의 데이터를 확인하세요
select * from country where GNP = 100 or GNP = 100000; -- 얘가 그 차이를 만듬

-- 14. country table 에서 독립년도(IndepYear) 가 1980 이후이면서 대륙(Continent) 이 Asia 가 아닌 나라의 자료를 조회하세요.
select * from country where IndepYear >= 1980 and continent != 'Asia';

-- 15. country table 에서 대륙(Continent) 이  'Europe', 'Asia', 'North America' 이 아닌 나라의 자료를, 대륙(Continent) 기준으로 오름차순, GDP 기준 내림차순으로 국가명, 대륙명, GNP 를 조회하시오.
select name, continent, gnp from country where continent not in ('Europe', 'Asia', 'North America') order by continent, GNP desc;

-- 16. city table 에서 도시명에 'S' 로 시작하고 중간에 'aa' 가 포함되는 나라를 모두 조회하세요.
select * from city where name like 'S%' and name like '%aa%';

-- 17. country table 에서 가장 면적이 큰 5개 나라의 이름, 대륙, 면적을 조회하세요.
select name, continent, SurfaceArea from country order by SurfaceArea desc limit 5; -- mysql 기준. oracle은 사용안함

-- offset

-- 18.
