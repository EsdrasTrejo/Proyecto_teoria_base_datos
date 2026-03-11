CREATE DATABASE IF NOT EXISTS presupuesto_personal_proyecto
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE presupuesto_personal_proyecto;

create table usuario(
id_usuario int auto_increment primary key,
nombre varchar(300) not null,
apellido varchar(300) not null,
correo varchar(300) not null unique,
fecha_registro date not null,
salario_mensual int not null, 
estado tinyint(1) not null default 1,
creador_user varchar(300) not null,
modificado_por_user varchar(300),
creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
modificado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP());

create table presupuesto(
id_presupuesto int auto_increment primary key,
id_usuario int not null ,
nombre varchar(300) not null,
year_inicio int not null,
mes_inicio int not null,
year_fin int not null,
mes_fin int not null,
total_ingresos decimal(12,2) not null default 0.00,
total_gastos decimal(12,2) not null default 0.00,
total_ahorro decimal(12,2) not null default 0.00,
fecha_hora_creacion timestamp not null default current_timestamp(),
estado tinyint(1) not null default 1,
creado_user varchar(300) not null ,
modificado_user varchar(300),
creado_fecha timestamp default current_timestamp() not null,
modificado_fecha timestamp not null default current_timestamp() on update current_timestamp(),
CONSTRAINT fk_presupuesto_usuario
FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
ON DELETE RESTRICT
ON UPDATE CASCADE
);

alter table categoria 
add id_usuario_propetario int not null;

create table categoria(
id_categoria int auto_increment primary key,
nombre varchar(300) not null,
descripcion varchar(1000),
tipo varchar(300) not null,
creado_user varchar(300)not null,
modificado_user varchar(300),
creado_fecha timestamp not null default current_timestamp(),
modificado_fecha timestamp not null default current_timestamp() on update current_timestamp()
);

create table subcategoria(
id_subcategoria int auto_increment primary key,
id_categoria int not null,
nombre varchar(300) not null,
descripcion varchar(300) not null,
indicador_activa tinyint (1) not null default 1,
es_defecto tinyint (1) not null default 1,
creado_user varchar(300) not null,
modificado_user varchar(300),
creado_fecha timestamp not null default current_timestamp(),
modificado_fecha timestamp not null default current_timestamp() on update current_timestamp(),
CONSTRAINT fk_subcategoria_categoria
FOREIGN KEY (id_categoria) 
REFERENCES categoria(id_categoria)
);

create table presupuesto_detalle(
id_presupuesto_detalle int auto_increment primary key,
id_presupuesto int not null,
id_subcategoria int not null,
monto_mensual decimal(12,2) not null,
observaciones varchar(1000),
creado_user varchar(300),
modificado_user varchar(300),
creado_fecha timestamp not null default current_timestamp(),
modificado_fecha timestamp not null default current_timestamp() on update current_timestamp(),
constraint fk_presupuesto_detalle_presupuesto
foreign key (id_presupuesto)
references presupuesto(id_presupuesto),
constraint fk_presupuesto_detalle_subcategoria
foreign key (id_subcategoria)
references subcategoria(id_subcategoria)
);

CREATE TABLE obligacion_fija (
  id_obligacion int auto_increment primary key,
  id_subcategoria int not null,
  nombre varchar(300) not null,
  descripcion varchar(500),
  monto_mensual decimal(12,2) not null,
  dia_vencimiento int not null ,
  vigente tinyint(1) not null default 1,
  fecha_inicio date,
  fecha_finalizacion date,
  creado_user varchar(300)not null,
  modificado_user varchar(300),
  creado_fecha timestamp not null default current_timestamp,
  modificado_fecha timestamp not null default current_timestamp() on update current_timestamp(),

  constraint fk_obligacion_subcategoria
  foreign key (id_subcategoria) 
  references subcategoria(id_subcategoria)
);

CREATE TABLE transaccion (
  id_transaccion int auto_increment primary key,
  id_presupuesto_detalle int not null,
  year int not null,
  mes int not null,
  tipo_transaccion varchar(300) not null,
  descripcion varchar(1000),
  monto decimal(12,2) not null,
  fecha date not null,
  metodo_pago varchar(300),
  num_factura varchar (300),
  observaciones varchar(1000),
  creado_user varchar(300) not null,
  modificado_user varchar(300),
  creado_fecha timestamp not null  default current_timestamp,
  modificado_fecha timestamp not null default current_timestamp on update current_timestamp,
  constraint fk_transaccion_presupuesto_detalle
  foreign key (id_presupuesto_detalle) 
  references presupuesto_detalle(id_presupuesto_detalle)
);

CREATE TABLE obligacionfija_transaccion (
  id_obligacion INT NOT NULL,
  id_transaccion INT NOT NULL,
  PRIMARY KEY (id_obligacion, id_transaccion),
  CONSTRAINT fk_ot_obligacion
    FOREIGN KEY (id_obligacion) REFERENCES obligacion_fija(id_obligacion),
  CONSTRAINT fk_ot_transaccion
    FOREIGN KEY (id_transaccion) REFERENCES transaccion(id_transaccion)
);

SELECT table_name, constraint_name
FROM information_schema.table_constraints
WHERE constraint_schema = DATABASE()
  AND constraint_type = 'FOREIGN KEY';




