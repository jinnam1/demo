--테이블 삭제
drop table product;

--시퀀스삭제
drop sequence product_product_id_seq;

---------
--상품관리
--------
create table product(
    product_id  number(10), --primary key
    pname       varchar(30),
    quantity    number(10),
    price       number(10)
    --primary key(product_id)
);
--기본키
alter table product add constraint product_product_id_pk primary key(product_id);

--시퀀스생성
create sequence product_product_id_seq;

--생성--
insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '컴퓨터', 5, 1000000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '모니터', 5, 500000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '프린터', 1, 300000);

--조회--
select product_id, pname, quantity, price
  from product
 where product_id = 2;

--수정--
update product
   set pname = '컴퓨터2',
       quantity = 10,
       price = 1200000
 where product_id = 1;

--삭제
delete from product where product_id = 1;

--전체조회-
select product_id,pname,quantity,price from product;

commit;