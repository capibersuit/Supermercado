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

--******************* HASTA ACA DEFINICIONES *****************************************


SELECT	* FROM productos 

SELECT NOW();