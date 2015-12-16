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


CREATE TABLE listas
(
  id serial NOT NULL,
  comentario text,
  fecha timestamp,
  CONSTRAINT id_pk_listas PRIMARY KEY (id )
)

CREATE TABLE rel_listas_productos
(
  id serial NOT NULL,
  id_compra Integer,
  id_prod Integer,
  cant Integer,
  precio double precision,
  CONSTRAINT id_pk_rel_listas_productos PRIMARY KEY (id)
)

--******************* HASTA ACA DEFINICIONES *****************************************


SELECT	* FROM productos 

SELECT	* FROM listas

SELECT	* FROM rel_listas_productos

SELECT NOW();


SELECT * FROM productos WHERE nombre= 'azucar'