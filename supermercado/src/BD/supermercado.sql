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

alter table rel_listas_productos
 add constraint FK_rel_listas_productos
  foreign key (id_prod)
  references productos(id);

alter table rel_listas_productos
 add constraint FK_rel_listas
  foreign key (id_compra)
  references listas(id);

--******************* HASTA ACA DEFINICIONES *****************************************


SELECT	* FROM productos 

SELECT	* FROM productos order by id;


SELECT	* FROM listas

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


SELECT * FROM rel_listas_productos 

where id_compra = 3


select * from productos where nombre = 'coca cola'