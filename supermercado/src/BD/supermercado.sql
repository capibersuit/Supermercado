-- Table: productos

-- DROP TABLE productos;

CREATE TABLE productos
(
  id serial NOT NULL,
  nombre text,
  precio double precision,
  CONSTRAINT id_pk_productos PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE productos
  OWNER TO postgres;

 alter table productos
 add constraint uunique_productos
 unique (nombre);


CREATE TABLE listas
(
  id serial NOT NULL,
  comentario text,
  fecha timestamp,
  CONSTRAINT id_pk_listas PRIMARY KEY (id )
)

ALTER TABLE listas ADD ver_marcados boolean
ALTER TABLE listas ADD pagado double precision
ALTER TABLE listas ADD desc_coto double precision


CREATE TABLE rel_listas_productos
(
  id serial NOT NULL,
  id_compra Integer,
  id_prod Integer,
  cant Integer,
  precio double precision,
  CONSTRAINT id_pk_rel_listas_productos PRIMARY KEY (id)
)

ALTER TABLE rel_listas_productos ADD esta_marcada boolean

ALTER TABLE rel_listas_productos ADD fecha_venc timestamp

ALTER TABLE rel_listas_productos ADD existe_todavia boolean default true


alter table rel_listas_productos
 add constraint FK_rel_listas_productos
  foreign key (id_prod)
  references productos(id);

alter table rel_listas_productos
 add constraint FK_rel_listas
  foreign key (id_compra)
  references listas(id);

alter table rel_listas_productos
  add constraint UQ_rel_listas
  unique (id_prod, id_compra);

--******************* HASTA ACA DEFINICIONES *****************************************


SELECT	* FROM productos 

SELECT	* FROM productos order by id;


SELECT	* FROM listas

update listas SET pagado = 1526

SELECT	* FROM rel_listas_productos

SELECT NOW();


SELECT * FROM productos WHERE nombre= 'azucar'



SELECT * FROM pg_stat_activity

DELETE FROM rel_listas_productos 
WHERE 
--id_compra= 44 or 
id_prod=4
 or id_prod=12


UPDATE rel_listas_productos
   SET esta_marcada= true, precio=99
 WHERE id_compra=15 and id_prod=98


select * from rel_listas_productos
	where id_prod = 185


SELECT * FROM rel_listas_productos 

where id_compra = 3


select * from productos where nombre = 'coca cola'


---------------- 16-12-2016 --------------------------
SELECT NOW();


SELECT * FROM rel_listas_productos 

	where fecha_venc < NOW()  + INTERVAL '30 DAY';



update rel_listas_productos set fecha_venc = '2016-12-27'

where id_compra = 24
	and id_prod = 273 


SELECT * FROM productos WHERE nombre ~* 'salchicha'



SELECT * FROM rel_listas_productos rlp, productos p
where rlp.id_prod = p.id
and (id_compra in (24,23,22,20))
	and nombre ~* 'carton'
order by  id_compra , nombre desc


SELECT * FROM rel_listas_productos rlp, productos p
where rlp.id_prod = p.id
and fecha_venc is not null



SELECT * FROM rel_listas_productos rlp, productos p
where rlp.id_prod = p.id
and rlp.id_compra = 26

order by p.nombre

SELECT id, id_compra, id_prod, cant, precio, esta_marcada, fecha_venc, 
       existe_todavia
  FROM rel_listas_productos 
	order by id_compra desc

  
update rel_listas_productos set existe_todavia = true 

, fecha_venc = '2017-06-06'

where id_compra = 26 
	and id_prod = 141

-- ---------------------- 03-02-2017 -----------------------------

SELECT * FROM rel_listas_productos rlp, productos p
where rlp.id_prod = p.id
and fecha_venc is not null
order by fecha_venc


update rel_listas_productos set fecha_venc = '2017-02-13' where id_compra = 27 	and id_prod = 771 

update rel_listas_productos set fecha_venc = '2017-02-25' where id_compra = 27 	and id_prod = 907

update rel_listas_productos set fecha_venc = '2017-02-23' where id_compra = 27 	and id_prod = 906

update rel_listas_productos set fecha_venc = '2017-02-23' where id_compra = 27 	and id_prod = 906

update rel_listas_productos set fecha_venc = '2017-02-23' where id_compra = 27 	and id_prod = 906

update rel_listas_productos set fecha_venc = '2017-02-26' where id_compra = 27 	and id_prod = 700

update rel_listas_productos set fecha_venc = '2017-03-02' where id_compra = 27 	and id_prod = 69

update rel_listas_productos set fecha_venc = '2017-05-25' , existe_todavia= true where id_compra = 23 	and id_prod = 197

update rel_listas_productos set fecha_venc = '2017-06-18' , existe_todavia= true where id_compra = 26 	and id_prod = 197 

update rel_listas_productos set fecha_venc = '2017-09-29' where id_compra = 27 	and id_prod = 68


-- ---------------------- 10-02-2017 -----------------------------

UPDATE rel_listas_productos
   SET esta_marcada= false WHERE id_compra=27

   

SELECT rlp.id_compra, rlp.fecha_venc, rlp.existe_todavia, p.* FROM rel_listas_productos rlp, productos p
where rlp.id_prod = p.id
and fecha_venc is not null and  existe_todavia
order by fecha_venc


update rel_listas_productos set fecha_venc = '2017-06-06' where id_compra = 27 	and id_prod = 143
update rel_listas_productos set fecha_venc = '2017-03-23' , existe_todavia= true where id_compra = 26 	and id_prod = 837
update rel_listas_productos set fecha_venc = '2017-09-20' where id_compra = 27 	and id_prod = 697
update rel_listas_productos set fecha_venc = '2017-06-25' where id_compra = 27 	and id_prod = 197

update rel_listas_productos set fecha_venc = '2017-05-18', existe_todavia= true where id_compra = 26 	and id_prod = 687
update rel_listas_productos set fecha_venc = '2017-09-26', existe_todavia= true where id_compra = 26 	and id_prod = 688
update rel_listas_productos set fecha_venc = '2017-04-06', existe_todavia= true where id_compra = 24 	and id_prod = 143
update rel_listas_productos set fecha_venc = '2017-06-24', existe_todavia= true where id_compra = 23 	and id_prod = 77
update rel_listas_productos set fecha_venc = '2017-06-05', existe_todavia= true where id_compra = 24 	and id_prod = 822

update rel_listas_productos set existe_todavia = false where id_compra = 26 and id_prod = 273 
update rel_listas_productos set existe_todavia = false where id_compra = 26 and id_prod = 273 

-- ---------------------- 16-02-2017 -----------------------------

SELECT rlp.id_compra, rlp.fecha_venc, rlp.existe_todavia, p.* FROM rel_listas_productos rlp, productos p
where rlp.id_prod = p.id
and fecha_venc is not null and  existe_todavia
order by fecha_venc


update rel_listas_productos set existe_todavia = false where id_compra = 27 and id_prod = 771 
update rel_listas_productos set existe_todavia = false where id_compra = 27 and id_prod = 906 
update rel_listas_productos set existe_todavia = false where id_compra = 27 and id_prod = 907 
update rel_listas_productos set existe_todavia = false where id_compra = 27 and id_prod = 69

-- ---------------------- 04-03-2017 -----------------------------

update rel_listas_productos set existe_todavia = false where id_compra = 27 and id_prod = 700 

-- ---------------------- 22-03-2017 -----------------------------

update rel_listas_productos set existe_todavia = false where id_compra = 26 and id_prod = 837 

-- ---------------------- 06-04-2017 -----------------------------

update listas set fecha = '01/03/2017' where id = 28
update listas set fecha = '05/04/2017' where id = 29


update rel_listas_productos set existe_todavia = false where id_compra = 24 and id_prod = 143


------------------------05-12-2017   pobando ejecutar desde dentro de eclipse !!!!! --------------------------------------


select * from productos
select * from listas



